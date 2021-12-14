<template>
  <div>
    <el-form :inline="true" :model="user" class="demo-form-inline">
      <el-form-item label="username">
        <el-input v-model="user.name" placeholder="user"></el-input>

      </el-form-item>
      <el-form-item label="password">
        <el-input v-model="user.pwd" placeholder="password" show-password></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="login">login</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from "axios"

export default {
  name: 'Login',
  props: {
    msg: String
  },
  data() {
    return {
      user: {
        name:"",
        pwd: ""
      }
    }
  },
  methods: {
    login() {
      axios.post("http://localhost:9853/api/v1/login/login",
          {name: this.user.name, pwd: this.user.pwd})
          .then(response => {
            if (response.data !='') {
              this.$router.push("/index");
              this.$cookies.set("user",response.data);
            } else {
              this.$alert(response.data, "Failed");
              this.user.name = "";
              this.user.pwd = "";
            }
          });
    },
  }
}
</script>

<style scoped>
h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
