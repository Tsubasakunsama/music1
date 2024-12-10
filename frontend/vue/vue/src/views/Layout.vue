<template>
    <div id="app">
    <el-container>
      <el-header style="background-color:#3636d7;">
        <img src="../assets/music1.png" alt="" style="width:40px;position:relative;top:10px">
        <span style="font-size:20px;margin-left:15px;color:white">Band Information System</span>
        <el-dropdown style="float:right;height:60px;line-height: 60px">
  <span class="el-dropdown-link" style="color: white;font-size: 15px; cursor: pointer;">{{user.name}}<i class="el-icon-arrow-down el-icon--right"></i></span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item><div @click="dialogVisible=true">Cancel Account</div></el-dropdown-item>
            <el-dropdown-item><div @click="dialogFormVisible=true">Log out</div></el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </el-header>
    </el-container>
      <el-dialog title="Tip" :visible.sync="dialogVisible" width="30%" >
        <span>Cancel your account?</span>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">Cancel</el-button>
    <el-button type="primary" @click="del(user.id)">Confirm</el-button>
  </span>
      </el-dialog>
      <el-dialog title="提示" :visible.sync="dialogFormVisible" width="30%" >
        <span>Log out?</span>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogFormVisible = false">Cancel</el-button>
    <el-button type="primary" @click="logout()">Confirm</el-button>
  </span>
      </el-dialog>
    <el-container>
     <el-aside style="overflow:hidden;height:100vh;background-color:#2b7cde;width:200px;float:left">
      <el-menu :default-active="$route.path" router class="el-menu-vertical-demo" background-color="#2b7cde" text-color="white" active-text-color="gray">
       <el-submenu index="/3d">
         <template slot="title">
           <i class="el-icon-headset" style="color: white"></i>
           <span>Band Information</span>
         </template>
           <el-menu-item index="/band">Band</el-menu-item>
           <el-menu-item index="/song">Song</el-menu-item>
           <el-menu-item index="/concert">Concert</el-menu-item>
           <el-menu-item index="/member">Member</el-menu-item>
           <el-menu-item index="/album">Album</el-menu-item>
           <el-menu-item index="/joins">Joins</el-menu-item>
           <el-menu-item index="/performs">Performs</el-menu-item>
       </el-submenu>
          <template slot="title">
            <i class="el-icon-s-custom" style="color: white"></i>
            <span>User Information</span>
          </template>
          <el-menu-item index="/data" style="margin-left: 32px">User</el-menu-item>
      </el-menu>
     </el-aside>

      <el-main style="height:100vh;" class="a">
        <router-view/>
      </el-main>
    </el-container>
  </div>

</template>
<script>
import request from "@/utils/request";

export default{
    name:'Layout',
  data(){
      return{
        user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},
        dialogVisible:false,
        dialogFormVisible:false,
      }
  },
  methods:{
      logout(){
        localStorage.removeItem("user");
        this.dialogFormVisible=false,
        this.$router.push("/");
      },
    del(id){
      request.delete(`/admin/${id}`).then(res=>{
        if(res.code==='0'){
          this.$message({
            message:'删除成功',
            type:'success'
          });
          localStorage.removeItem("user");
          this.dialogVisible = false;
          this.$router.push("/");
        }else{
          this.$message({
            message:res.msg,
            type:"error"
          });
        }
      })

    }
  }
}
</script>
<style>
.el-menu{
    border-right:none !important;
}
.a{
  background-image: url("../assets/music (2).png");
  background-position:center bottom;
  background-size: cover;
  background-repeat: no-repeat;
  opacity:0.9;
}
</style>