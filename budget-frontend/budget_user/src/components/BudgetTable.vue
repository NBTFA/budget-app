<template>
  <div>
    <!-- 搜索框和日期选择器 -->
    <div class="search-area">
      <el-input
        v-model="searchText"
        placeholder="请输入搜索内容"
        class="table-search"
      ></el-input>
      <el-date-picker
        v-model="selectedDate"
        type="date"
        placeholder="选择日期"
        format="yyyy-MM-dd"
        value-format="yyyy-MM-dd"
        @change="filterByDate"
        class="date-picker"
      ></el-date-picker>
    </div>
    <!-- 表格 -->
    <el-table :data="filteredData" style="width: 100%">
      <!-- 展开列 -->
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" inline class="table-expand">
            <el-form-item label="事物名">
              <span>{{ props.row.name }}</span>
            </el-form-item>
            <el-form-item label="日期">
              <span>{{ props.row.record_date }}</span>
            </el-form-item>
            <el-form-item label="收支">
              <span>{{ props.row.gain ? "收入" : "支出" }}</span>
            </el-form-item>
            <el-form-item label="分类">
              <span>{{ props.row.category }}</span>
            </el-form-item>
            <el-form-item label="描述">
              <span>{{ props.row.description }}</span>
            </el-form-item>
            <el-form-item label="金额">
              <span>{{ props.row.amount }}</span>
            </el-form-item>
            <!-- <el-form-item label="Tags">
              <el-tag v-for="tag in props.row.tags" :key="tag">
                {{ tag }}
              </el-tag>
            </el-form-item> -->
          </el-form>
        </template>
      </el-table-column>

      <!-- 数据列 -->
      <el-table-column
        prop="record_date"
        label="日期"
        width="120"
      ></el-table-column>
      <el-table-column
        prop="name"
        label="商品名称"
        width="150"
      ></el-table-column>
      <el-table-column
        prop="category"
        label="分类"
        width="120"
      ></el-table-column>
      <el-table-column label="收支" width="100">
        <template slot-scope="scope">
          <span>{{ scope.row.gain ? "收入" : "支出" }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="amount" label="金额" width="100"></el-table-column>
      <!-- <el-table-column label="Tag">
        <template slot-scope="scope">
          <div v-for="tag in scope.row.tags" :key="tag" class="table-tag">
            <el-tag closable @close="handleTagClose(scope.$index, tag)">
              {{ tag }}
            </el-tag>
          </div>
          <el-input
            class="input-new-tag"
            v-if="scope.row.editingTag"
            v-model="newTag"
            size="mini"
            ref="saveTagInput"
            @keyup.enter.native="handleInputConfirm(scope.$index)"
            @blur="handleInputConfirm(scope.$index)"
          ></el-input>
          <el-button size="mini" @click="showInput(scope.$index)"
            >+ 新标签</el-button
          >
        </template>
      </el-table-column> -->
      <el-table-column label="操作" width="100">
        <template slot-scope="scope">
          <el-button
            type="danger"
            size="small"
            @click="confirmDelete(scope.$index, scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="确认删除"
      :visible.sync="dialogVisible"
      width="30%"
      @close="resetDeleteConfirmation"
    >
      <span>确定要删除这项记录吗？</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleDelete">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
  
  <script>
export default {
  data() {
    return {
      tableData: [
        // 表格原始数据
        // 示例数据
        {
          record_date: "2023-12-25",
          name: "商品A",
          category: "类别1",
          description: "商品A的描述",
          amount: 200,
          tags: ["标签1", "标签2"],
          editingTag: false,
        },
        // 更多数据...
      ],
      newTag: "",
      searchText: "",
      selectedDate: "",
      filteredByDateData: [],
      dialogVisible: false, // 控制对话框显示
      deleteIndex: null, // 要删除的行的索引
      deleteRow: null, // 要删除的行的数据
    };
  },
  created() {
    this.tableData = this.$store.state.budgets;
  },
  computed: {
    budgets() {
      return this.$store.state.budgets;
    },
    filteredData() {
      let data = this.selectedDate ? this.filteredByDateData : this.budgets;
      if (this.searchText) {
        return data.filter(
          (item) =>
            item.name.toLowerCase().includes(this.searchText.toLowerCase()) ||
            item.category
              .toLowerCase()
              .includes(this.searchText.toLowerCase()) ||
            item.amount.toString().includes(this.searchText) ||
            (item.tags &&
              item.tags.some((tag) =>
                tag.toLowerCase().includes(this.searchText.toLowerCase())
              ))
        );
      }
      return data;
    },
  },
  watch: {
    "$store.state.budgets": function (newVal, oldVal) {
      this.tableData = newVal;
    },
  },
  methods: {
    getBudgets() {
      return this.$store.state.budgets;
    },
    // 弹出删除确认对话框
    confirmDelete(index, row) {
      this.dialogVisible = true;
      this.deleteIndex = index;
      this.deleteRow = row;
    },

    // 处理删除操作
    handleDelete() {
      this.tableData.splice(this.deleteIndex, 1);
      // 这里可以添加与服务器的交互逻辑
      this.$http
        .delete("/user/budget", {
          data: {
            id: this.deleteRow.id,
          },
        })
        .then((res) => {
          console.log("删除预算：", res);
          if (res.data.code === 20000) {
            this.$message.success("删除成功");
          } else {
            this.$message.error(res.data.message);
          }
        });
      console.log(`删除了行: ${this.deleteRow.name}`);
      this.resetDeleteConfirmation();
      this.$store.commit("setBudgets", this.tableData);
    },

    // 重置删除确认
    resetDeleteConfirmation() {
      this.dialogVisible = false;
      this.deleteIndex = null;
      this.deleteRow = null;
    },
    filterByDate() {
      if (this.selectedDate) {
        this.filteredByDateData = this.tableData.filter(
          (item) => item.record_date === this.selectedDate
        );
      } else {
        this.filteredByDateData = this.tableData;
      }
    },
    showInput(index) {
      this.tableData[index].editingTag = true;
      this.$nextTick(() => {
        this.$refs.saveTagInput[index].focus();
      });
    },
    handleInputConfirm(index) {
      if (this.newTag) {
        this.tableData[index].tags.push(this.newTag);
        this.newTag = "";
      }
      this.tableData[index].editingTag = false;
    },
    handleTagClose(index, tag) {
      this.tableData[index].tags.splice(
        this.tableData[index].tags.indexOf(tag),
        1
      );
    },
  },
};
</script>
  
  <style>
.table-expand {
  font-size: 0;
}
.table-expand label {
  width: 90px;
  color: #99a9bf;
}
.table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 50%;
}

.search-area {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}
.table-search {
  flex-grow: 1;
  margin-right: 10px;
}

.table-tag {
  display: inline-block;
  margin-right: 5px;
}
.input-new-tag {
  width: 100px;
  margin-right: 10px;
}
</style>
  