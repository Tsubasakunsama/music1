<template>
  <div style="height:100vh;overflow:hidden;display:flex;align-items:center;justify-content: center;" class="back">
    <!-- Outer container -->
    <div style="display: flex;background:linear-gradient(to bottom,#1e54de,mediumpurple);width:60%;border-radius:5px;overflow: hidden;height:60%">
      <!-- Inner container -->
      <div style="flex:1;display:flex;justify-content: center;align-items:center;"> <!-- Left side image -->
        <img src="../assets/3.png" alt="" style="height:66%;width:80%;margin-left:70px;margin-right: 30px;">
      </div>
      <div style="flex:1;display:flex;align-items:center;justify-content: center">  <!-- Right side form -->
        <el-form :model="user" style="width:80%" :rules="rules" ref="registerRef">
          <div style="font-size:20px;font-weight:bold;text-align: center;margin-bottom: 20px;">Welcome to the Band Information System</div> <!-- Title -->
          <el-form-item prop="username">
            <el-input prefix-icon="el-icon-user" size="medium" placeholder="Enter username" v-model="user.name"></el-input>
            <!-- Username -->
          </el-form-item>
          <el-form-item prop="password">
            <el-input  prefix-icon="el-icon-lock" size="medium" show-password placeholder="Enter password" v-model="user.password"></el-input>
            <!-- Password -->
          </el-form-item>
          <el-form-item  prop="confirmPass">  <!-- Confirm password -->
            <el-input prefix-icon="el-icon-lock" size="medium" show-password placeholder="Confirm password" v-model="user.confirmPass"></el-input>
          </el-form-item>
          <el-form-item style="">
            <el-button type="primary" style="width:100%" @click="register">Register</el-button>
          </el-form-item>
          <div style="display:flex">
            <div >Already have an account? Please <span style="color:purple;cursor:pointer" @click="$router.push('/')">Login</span></div>
          </div>

        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import request from "@/utils/request";

export default {
  name: 'Register',
  data() {
    const validatePassword = (rule, confirmPass, callback) => {
      if (confirmPass === '') {
        callback(new Error('Please confirm your password'));
      } else if (confirmPass !== this.user.password) {
        callback(new Error('The passwords do not match'));
      } else {
        callback()
      }
    }

    return {
      user: {
        name: '',
        password: '',
        confirmPass: ''
      },
      rules: {
        name: [
          { required: true, message: 'Please enter your username', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'blur' },
        ],
        confirmPass: [
          { validator: validatePassword, trigger: 'blur' }
        ],
      }
    }
  },
  methods: {
    register() {
      this.$refs['registerRef'].validate((valid) => {
        if (valid) {
          request.post('/admin/register', this.user).then(res => {
            if (res.code === '0') {
              this.$message({
                message: "Registration successful",
                type: "success"
              });
              this.$router.push('/'); // Redirect to login
            } else {
              this.$message({
                message: res.msg,
                type: "error"
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
.back {
  background-image: url("../assets/music.png");
  background-size: cover;
}
</style>
