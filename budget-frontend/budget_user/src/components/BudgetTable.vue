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
              <span>{{ props.row.date }}</span>
            </el-form-item>
            <el-form-item label="收支">
              <span>{{ props.row.gain }}</span>
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
            <el-form-item label="Tags">
              <el-tag v-for="tag in props.row.tags" :key="tag">
                {{ tag }}
              </el-tag>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <!-- 数据列 -->
      <el-table-column prop="date" label="日期" width="120"></el-table-column>
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
      <el-table-column prop="gain" label="收支" width="100"></el-table-column>
      <el-table-column prop="amount" label="金额" width="100"></el-table-column>
      <el-table-column label="Tag">
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
      </el-table-column>
    </el-table>
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
          date: "2023-12-25",
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
    };
  },
  created() {
    this.$http.get("/user/budget/all").then((res) => {
      console.log("获取用户预算：", res);
      if (res.data.status === 20000) {
        this.tableData = res.data.data.budgetList;
      } else {
        this.$message.error(res.data.message);
      }
    });
  },
  computed: {
    filteredData() {
      let data = this.selectedDate ? this.filteredByDateData : this.tableData;
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
  methods: {
    filterByDate() {
      if (this.selectedDate) {
        this.filteredByDateData = this.tableData.filter(
          (item) => item.date === this.selectedDate
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
  