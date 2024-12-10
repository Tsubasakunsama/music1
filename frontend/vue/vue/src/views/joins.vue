<template>
  <div style="margin-bottom: 20px;">
    <div>
      <el-input style="width: 100px;" v-model="params.cid" placeholder="Enter cid"></el-input>
      <el-input style="width: 100px; margin-left: 5px;" v-model="params.bname" placeholder="Enter Band Name"></el-input>
      <el-button type="primary" style="margin-left: 10px;" @click="findbySearch()">Search</el-button>
      <el-button type="warning" style="margin-left: 10px;" @click="reset()">Reset</el-button>
      <el-button type="danger" style="margin-left: 10px;" @click="add()">Add Join</el-button>
    </div>

    <div>
      <el-table :data="tableData" style="width: 100%; margin-top: 10px">
        <el-table-column prop="cid" label="Concert ID"></el-table-column>
        <el-table-column prop="bname" label="Band Name"></el-table-column>
        <el-table-column label="Actions">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)">Edit</el-button>
            <el-popconfirm title="Are you sure you want to delete?" >
              <el-button slot="reference" size="small" type="danger" style="margin-left: 10px" @click="del(scope.row.cid)">Delete</el-button>
            </el-popconfirm>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div style="margin-top: 10px;">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="params.pageNum"
          :page-sizes="[5, 10, 15, 20]"
          :page-size="params.pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          :page-size-text="pageSizeText">
      </el-pagination>
    </div>

    <div>
      <el-dialog title="Join Information" :visible.sync="dialogFormVisible" width="30%" :modal="false">
        <el-form :model="form">
          <el-form-item label="Concert ID" label-width="20%">
            <el-input v-model="form.cid" autocomplete="off" style="width: 80%; margin-left: 20px"></el-input>
          </el-form-item>
          <el-form-item label="Band Name" label-width="20%">
            <el-input v-model="form.bname" autocomplete="off" style="width: 80%; margin-left: 20px"></el-input>
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
  name: 'joins',
  data() {
    return {
      params: {
        cid: '',
        bname: '',
        pageNum: 1,
        pageSize: 5
      },
      total: 0,
      tableData: [],
      dialogFormVisible: false,
      form: {},
    };
  },
  created() {
    this.findbySearch();
  },
  methods: {
    findbySearch() {
      request.get('/joins/search', { params: this.params })
          .then(response => {
            console.log(response.data.list);
            this.tableData = response.data.list.map(item => ({
              cid: item.cid,
              bname: item.bname
            }));
            this.total = response.data.total;
          })
          .catch(error => {
            console.error(error);
          });
    },
    reset() {
      this.params.cid = '';
      this.params.bname = '';
      this.findbySearch();
    },
    handleSizeChange(val) {
      this.params.pageSize = val;
      this.findbySearch();
    },
    handleCurrentChange(val) {
      this.params.pageNum = val;
      this.findbySearch();
    },
    add() {
      this.form = {};
      this.dialogFormVisible = true;
    },
    edit(obj) {
      this.form = obj;
      this.dialogFormVisible = true;
    },
    submit() {
      const dataToSubmit = {
        cid: this.form.cid,
        bname: this.form.bname
      };
      request.post("/joins", dataToSubmit)
          .then(res => {
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
    del(cid) {
      request.delete(`/joins/${cid}`).then(res => {
        if (res.code === '0') {
          this.$message({
            message: 'Deletion successful',
            type: 'success'
          });
          this.findbySearch();
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
