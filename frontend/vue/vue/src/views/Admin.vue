<template>
  <div style="margin-bottom: 20px;">
    <div>
      <el-input style="width:100px" v-model="params.name" placeholder="请输入姓名"></el-input>
      <el-input style="width:100px;margin-left: 5px;" v-model="params.sex"placeholder="请输入性别"></el-input>
      <el-input style="width:100px;margin-left: 5px;" v-model="params.age"placeholder="请输入年龄"></el-input>
      <el-input style="width:100px;margin-left: 5px;" v-model="params.school"placeholder="请输入学校"></el-input>
      <el-button type=primary  style="margin-left: 10px;" @click="findbySearch()">查询</el-button>
      <el-button type="warning"  style="margin-left: 10px;" @click="reset()">清空</el-button>
    </div>
    <div>
    <el-table :data="tableData" style="width: 100%;margin-top:10px">
      <el-table-column prop="name"label="姓名"></el-table-column>
      <el-table-column prop="sex" label="性别"></el-table-column>
      <el-table-column prop="age"label="年龄"></el-table-column>
      <el-table-column prop="school"label="学校"></el-table-column>

    </el-table>

    </div>
    <div style="margin-top: 10px;">
    <el-pagination @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="params.pageNum"
      :page-sizes="[5, 10, 15, 20]"
      :page-size="params.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
  </div>
    <div>
      <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
        <el-form :model="form">
          <el-form-item label="姓名" label-width="15%">
            <el-input v-model="form.name" autocomplete="off" style="width:90%"></el-input>
          </el-form-item>
          <el-form-item label="性别" label-width="15%">
            <el-radio v-model="form.sex" label="男">男</el-radio>
            <el-radio v-model="form.sex" label="女">女</el-radio>
          </el-form-item>
          <el-form-item label="年龄" label-width="15%">
            <el-input v-model="form.age" autocomplete="off" style="width:90%"></el-input>
          </el-form-item>
          <el-form-item label="学校" label-width="15%">
            <el-input v-model="form.school" autocomplete="off" style="width:90%"></el-input>
          </el-form-item>
          <el-form-item label="电话" label-width="15%">
            <el-input v-model="form.phone" autocomplete="off" style="width:90%"></el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="determine()">确 定</el-button>
        </div>
      </el-dialog>
    </div>
    </div>
  </template>

  <script>
  import request from '@/utils/request';
    export default {
      name: 'Admin',
      data() {
        return {
          params: {
            name: '',
            sex:'',
            age:'',
            school: '',
            pageNum:1,
            pageSize:5

          },
          total: 0,
          tableData: [],
          dialogFormVisible:false,
          form:{},
        }
      },
      created() {
        this.findbySearch();
      },
      methods: {
        findbySearch() {
          request.get('/admin/search', {
            params: this.params
          }).then(res => {
            if (res.code === '0') {
              this.tableData = res.data.list;
              this.total=res.data.total;
            } else {
              this.$message({
                message: res.msg,
                type: 'error'
              });
            }
          })
        },
        reset(){
          this.params={
            name:'',
            school:'',
            pageNum:1,
            pageSize:5

          }
          this.findbySearch();
        },
        handleSizeChange(pageSize){
          this.params.pageSize=pageSize;
          this.findbySearch();
        },
        handleCurrentChange(pageNum){
          this.params.pageNum=pageNum;
          this.findbySearch();
        },

      }
    }
    
  </script>