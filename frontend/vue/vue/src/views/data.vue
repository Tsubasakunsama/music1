<template>
  <div style="margin-top: 20px;">
   <div style="margin:10px;overflow: hidden;background-color: white;opacity:0.8;height:80vh">
     <div style="text-align: center;margin-top: 80px;font-size: 20px">Welcome,<span style="color: #329bff">{{user.name}}</span>!</div>
     <div style="margin-top:80px;font-size: 20px;margin-left:40px;font-weight:bold;">Individual Information</div>
     <div style="display: flex">
     <div style="margin-left:40px;margin-top:40px;flex:1;font-size: 20px"><i class="el-icon-user"></i>Name:{{user.name}}</div>
     <div style="margin-top:40px;flex:1;font-size: 20px"><i class="el-icon-male"></i>Sex:{{user.sex}}</div>
     <div style="margin-top:40px;flex:1;font-size: 20px"><i class="el-icon-time"></i>Age:{{user.age}}</div>
     </div>
     <div style="margin-left:40px;margin-top:40px;font-size: 20px"><i class="el-icon-mobile-phone"></i>Phone:{{user.phone}}</div>
     <div style="margin-left:40px;margin-top:40px;margin-bottom: 20px;font-size: 20px"><i class="el-icon-school"></i>School:{{user.school}}</div>
     <div>
       <el-button style="margin-top:60px;float:right;margin-right:40px" type="danger" @click="dialogVisible=true"><i class="el-icon-document-delete"></i>Log out</el-button>
     <el-button style="margin-top:60px;float:right;margin-right:10px " type="primary" @click="edit()"><i class="el-icon-edit"></i>Edit</el-button>
     </div>
   </div>
    <div>
      <el-dialog title="User Information" :visible.sync="dialogFormVisible" width="30%" :modal="false">
        <el-form :model="user">
          <el-form-item label="Name" label-width="20%">
            <el-input v-model="user.name" autocomplete="off" style="width:80%;margin-left: 20px;"></el-input>
          </el-form-item>
          <el-form-item label="Sex" label-width="20%">
            <el-radio v-model="user.sex" label="Male" style="margin-left: 20px;">Male</el-radio>
            <el-radio v-model="user.sex" label="Female">Female</el-radio>
          </el-form-item>
          <el-form-item label="Age" label-width="20%">
            <el-input v-model="user.age" autocomplete="off" style="width:80%;margin-left: 20px;"></el-input>
          </el-form-item>
          <el-form-item label="School" label-width="20%">
            <el-input v-model="user.school" autocomplete="off" style="width:80%;margin-left: 20px;"></el-input>
          </el-form-item>
          <el-form-item label="Phone" label-width="20%">
            <el-input v-model="user.phone" autocomplete="off" style="width:80%;margin-left: 20px;"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">Cancel</el-button>
          <el-button type="primary" @click="determine()">Confirm</el-button>
        </div>
      </el-dialog>
    </div>
    <el-dialog title="Tip" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <span>Log out?</span>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">Cancel</el-button>
    <el-button type="primary" @click="logout()">Confirm</el-button>
  </span>
    </el-dialog>
  </div>

</template>

<script>
import request from '@/utils/request';
export default {
  name: 'data',
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      dialogFormVisible: false,
      dialogVisible:false,
    }
  },
    methods: {
      edit() {
        this.dialogFormVisible = true;
      },
      determine(){
        request.post('/admin',this.user).then(res=>{
          if(res.code==='0'){
            this.$message({
              message:'Success!',
              type:'success'
            });
            localStorage.setItem("user",JSON.stringify(res.data));
            this.dialogFormVisible=false;
          }
          else{
            this.$message({
              message: res.msg,
              type: 'error'
            });
          }
        })
      },
      logout(){
        localStorage.removeItem("user");
        this.dialogVisible = false,
        this.$router.push("/");
      },

      }

}



</script>