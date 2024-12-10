<template>
  <div style="margin-bottom: 20px;">
    <div>
      <el-input style="width: 100px;" v-model="params.CID" placeholder="Enter CID"></el-input>
      <el-input style="width: 100px; margin-left: 5px;" v-model="params.ctime" placeholder="Enter Time"></el-input>
      <el-input style="width: 100px; margin-left: 5px;" v-model="params.cplace" placeholder="Enter Place"></el-input>
      <el-button type="primary" style="margin-left: 10px;" @click="findbySearch()">Search</el-button>
      <el-button type="warning" style="margin-left: 10px;" @click="reset()">Reset</el-button>
      <el-button type="danger" style="margin-left: 10px;" @click="add()">Add Concert</el-button>
    </div>

    <div>
      <el-table :data="tableData" style="width: 100%; margin-top: 10px">
        <el-table-column prop="CID" label="Concert ID"></el-table-column>
        <el-table-column prop="ctime" label="Concert Time"></el-table-column>
        <el-table-column prop="cplace" label="Concert Place"></el-table-column>
        <el-table-column label="Actions">
          <template slot-scope="scope">
            <el-button type="primary" @click="edit(scope.row)">Edit</el-button>
            <el-popconfirm title="Are you sure you want to delete?" >
              <el-button slot="reference" size="small" type="danger" style="margin-left: 10px" @click="del(scope.row.CID)">Delete</el-button>
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
      <el-dialog title="Concert Information" :visible.sync="dialogFormVisible" width="30%" :modal="false">
        <el-form :model="form">
          <el-form-item label="Concert ID" label-width="20%">
            <el-input v-model="form.CID" autocomplete="off" style="width: 80%; margin-left: 20px"></el-input>
          </el-form-item>
          <el-form-item label="Concert Time" label-width="20%">
            <el-input v-model="form.ctime" autocomplete="off" style="width: 80%; margin-left: 20px"></el-input>
          </el-form-item>
          <el-form-item label="Concert Place" label-width="20%">
            <el-input v-model="form.cplace" autocomplete="off" style="width: 80%; margin-left: 20px"></el-input>
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
  name: 'concert',
  data() {
    return {
      params: {
        CID: '',
        ctime: '',
        cplace: '',
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
      request.get('/concert/search', { params: this.params })
          .then(response => {
            this.tableData = response.data.list.map(item => ({
              CID: item.cid,
              ctime: item.ctime,
              cplace: item.cplace
            }));
            this.total = response.data.total;
          })
          .catch(error => {
            console.error(error);
          });
    },

    reset() {
      this.params.CID = '';
      this.params.cplace = '';
      this.params.ctime = '';
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
      this.form = {};  // 清空表单数据
      this.dialogFormVisible = true;
    },

    edit(obj) {
      this.form = obj;
      this.dialogFormVisible = true;
    },

    submit() {
      const dataToSubmit = {
        CID: this.form.CID,
        ctime: this.form.ctime,
        cplace: this.form.cplace
      };

      request.post("/concert", dataToSubmit)
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

    del(CID) {
      request.delete(`/concert/${CID}`).then(res => {
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
