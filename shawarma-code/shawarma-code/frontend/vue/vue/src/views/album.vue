<template>
  <div style="margin-bottom: 20px;">
    <div>
      <el-input style="width:120px" v-model="params.aname" placeholder="Enter Album Name"></el-input>
      <el-input style="width:120px;margin-left: 5px;" v-model="params.aband" placeholder="Enter Band Name"></el-input>
      <el-input style="width:120px;margin-left: 5px;" v-model="params.atime" placeholder="Enter Date"></el-input>
      <el-input style="width:120px;margin-left: 5px;" v-model="params.company" placeholder="Enter Company"></el-input>
      <el-button type="primary" style="margin-left: 10px;" @click="findbySearch()">Search</el-button>
      <el-button type="warning" style="margin-left: 10px;" @click="reset()">Reset</el-button>
      <el-button type="danger" style="margin-left: 10px;" @click="add()">Add Album</el-button>
    </div>
    <div>
      <el-table :data="tableData" style="width: 100%; margin-top: 10px">
        <el-table-column prop="aname" label="Album Name"></el-table-column>
        <el-table-column prop="aband" label="Band Name"></el-table-column>
        <el-table-column prop="atime" label="Release Date"></el-table-column>
        <el-table-column prop="company" label="Company"></el-table-column>
        <el-table-column label="Actions">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)">Edit</el-button>
            <el-popconfirm title="Are you sure you want to delete?">
              <el-button slot="reference" size="small" type="danger" style="margin-left: 10px" @click="del(scope.row.aname)">Delete</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
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
      <el-dialog title="Album Information" :visible.sync="dialogFormVisible" width="30%" :modal="false">
        <el-form :model="form">
          <el-form-item label="Album Name" label-width="30%">
            <el-input v-model="form.aname" autocomplete="off" style="width:80%;margin-left: 20px"></el-input>
          </el-form-item>
          <el-form-item label="Band Name" label-width="30%">
            <el-input v-model="form.aband" autocomplete="off" style="width:80%;margin-left: 20px"></el-input>
          </el-form-item>
          <el-form-item label="Release Date" label-width="30%">
            <el-input v-model="form.atime" placeholder="Enter Date" style="width:80%;margin-left: 20px"></el-input>
          </el-form-item>
          <el-form-item label="Company" label-width="30%">
            <el-input v-model="form.company" placeholder="Enter Company" style="width:80%;margin-left: 20px"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
          <el-button type="primary" @click="submit()">Confirm</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request';

export default {
  name: 'Album',
  data() {
    return {
      params: {
        aname: '',
        aband: '',
        atime: '',
        company: '',
        pageNum: 1,
        pageSize: 5
      },
      total: 0,
      tableData: [],
      dialogFormVisible: false,
      form: {}
    };
  },
  created() {
    this.findbySearch();
  },
  methods: {
    // 查询方法
    findbySearch() {
      request.get('/album/search', { params: this.params })
          .then(res => {
            if (res.code === '0') {
              this.tableData = res.data.list;
              this.total = res.data.total;
            } else {
              this.$message({
                message: res.msg,
                type: 'error'
              });
            }
          });
    },

    // 重置搜索条件
    reset() {
      this.params = {
        aname: '',
        aband: '',
        atime: '',
        company: '',
        pageNum: 1,
        pageSize: 5
      };
      this.findbySearch();
    },

    // 分页变化时处理
    handleSizeChange(pageSize) {
      this.params.pageSize = pageSize;
      this.findbySearch();
    },

    handleCurrentChange(pageNum) {
      this.params.pageNum = pageNum;
      this.findbySearch();
    },

    // 添加专辑
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },

    // 编辑专辑
    edit(obj) {
      this.form = obj;
      this.dialogFormVisible = true;
    },

    // 提交表单
    submit() {
      request.post("/album", this.form).then(res => {
        if (res.code === '0') {
          this.$message({
            message: "Operation successful",
            type: 'success'
          });
          this.dialogFormVisible = false;
          this.findbySearch();
        } else {
          this.$message({
            message: res.msg,
            type: 'error'
          });
        }
      });
    },

    // 删除专辑
    del(aname) {
      request.delete(`/album/${aname}`).then(res => {
        if (res.code === '0') {
          this.$message({
            message: 'Deletion successful',
            type: 'success'
          });
        } else {
          this.$message({
            message: res.msg,
            type: "error"
          });
        }
      });
    }
  }
};
</script>
