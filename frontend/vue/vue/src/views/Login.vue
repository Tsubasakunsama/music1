<template>
  <div style="height:100vh;overflow:hidden;display:flex;align-items:center;justify-content: center;align-items:center;justify-content: center;" class="back">
    <div style="display: flex;background:linear-gradient(to bottom,mediumpurple,#1e54de);width:60%;border-radius:5px;overflow: hidden;height:60%">
      <div style="flex:1;display:flex;justify-content: center;align-items:center;">
        <img src="../assets/2.png" alt="" style="height:66%;width:80%;margin-left:70px;margin-right: 30px">
      </div>
      <div style="flex:1;display:flex;align-items:center;justify-content: center">
        <el-form :model="admin" style="width:80%" :rules="rules" ref="loginRef">
          <div style="font-size:20px;font-weight:bold;text-align: center;margin-bottom: 20px;">Welcome to the Band Information System</div> <!-- Title -->
          <el-form-item prop="name">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="Enter username" v-model="admin.name"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input  prefix-icon="el-icon-lock" size="medium" show-password placeholder="Enter password" v-model="admin.password"></el-input>
          </el-form-item>
          <el-form-item  prop="code">
            <div style="display:flex">
              <el-input  prefix-icon="el-icon-circle-check" size="medium" placeholder="Enter verification code" style="flex:1" v-model="admin.code"></el-input> <!-- Left input field -->
              <div style="height:36px;flex:1">
                <valid-code @update:value="getCode" ref="validcode"/>
              </div>
            </div>
          </el-form-item>
          <el-form-item style="">
            <el-button type="primary" style="width:100%" @click="login()">Login</el-button>
          </el-form-item>
          <div style="display:flex">
            <div >Don't have an account? Please <span style="color:blue;cursor:pointer" @click="$router.push('/register')">Register</span></div>
          </div>

        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import ValidCode from "@/components/ValidCode.vue"
import request from "@/utils/request"


export default {
  name: 'Login',
  components:{
    ValidCode
  },
  data(){
    const validateCode = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('Please enter the verification code'));
      } else if(value!==this.code){
        callback(new Error('Incorrect verification code'));
        this.$refs.validcode.createdCode();
      }else{
        callback()
      }
    }

    return{
      code:'',
      admin:{
        code:'',
        name:'',
        password:''
      },
      rules: {
        name: [
          { required: true, message: 'Please enter username', trigger: 'blur' },

        ],
        password: [
          { required: true, message: 'Please enter password', trigger: 'blur' },

        ],
        code: [
          { validator: validateCode, trigger: 'blur' }
        ],
      }
    }

  },
  created(){
  },
  methods:{
    getCode(code){
      this.code=code
    },
    login(){
      this.$refs['loginRef'].validate((valid) => {
        if (valid) {
          request.post('/admin/login',this.admin).then(res=>{
            if(res.code==='0'){
              this.$message({
                message:"Login successful",
                type:"success"
              });
              localStorage.setItem("user",JSON.stringify(res.data));
              res.data;
              this.$router.push('/layout');
            }else {
              this.$message({
                message:res.msg,
                type:"error"
              });
            }
          })
        }
      })
    },
  }
}
</script>

<style>
.back{
  background-image: url("../assets/music.png");
  background-size: cover;
}
</style>
