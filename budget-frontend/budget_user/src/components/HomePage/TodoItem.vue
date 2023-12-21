<template>
    <div class="todo-item">
      <el-checkbox v-model="checked" :disabled="checked" @change="onCheckboxChange"></el-checkbox>
      <span :class="['description', { 'is-complete': checked }]">{{ description }}</span>
      <span class="date" v-if="checked">{{ formatDate(completedDate) }}</span>
      <el-dialog
        title="选择完成日期"
        :visible.sync="dialogVisible"
        width="30%"
        @close="onDialogClose"
      >
        <el-date-picker
          v-model="datePickerModel"
          type="date"
          placeholder="选择日期"
          value-format="yyyy-MM-dd"
        ></el-date-picker>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitCompleted">确定</el-button>
        </span>
      </el-dialog>
    </div>
  </template>
  
  <script>
  export default {
    name: 'TodoItem',
    props: {
      description: {
        type: String,
        required: true
      },
      // 从父组件接收已完成事项的日期
      completedDate: {
        type: Date,
        default: () => null // 默认为 null，表示未完成
      }
    },
    data() {
      return {
        checked: false,
        dialogVisible: false,
        datePickerModel: null // 用户在日期选择器中选择的日期
      };
    },
    methods: {
      onCheckboxChange(value) {
        if (value) {
          this.dialogVisible = true;
        }
      },
      formatDate(date) {
        if (!date) return ''; // 如果日期不存在，则返回空字符串
        const d = new Date(date);
        return `${d.getFullYear()}|${d.getMonth() + 1}|${d.getDate()}`;
      },
      submitCompleted() {
        if (this.datePickerModel) {
          this.completedDate = this.datePickerModel;
          // TODO: 提交完成的事项到后端
          this.checked = true; // 确保勾选框被选中
        }
        this.dialogVisible = false;
      },
      onDialogClose() {
        // 如果没有选择日期，取消勾选复选框
        if (!this.datePickerModel) {
          this.checked = false;
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .todo-item {
    display: flex;
    align-items: center;
  }
  
  .is-complete {
    text-decoration: line-through;
  }

  .date {
    font-size: x-small;
  }

  .description {
    font-size: x-small;
    margin-right: 10px;
    margin-left: 5px;
  }
  </style>
  