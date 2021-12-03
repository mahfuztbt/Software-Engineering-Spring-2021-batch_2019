<template>
  <el-container>
    <el-header>
      <el-menu :default-active='a1' mode="horizontal" background-color="#545c64" text-color="#fff"
               active-text-color="#ffd04b" style="float:right">
        <el-menu-item index="1" class="el-icon-user-solid">{{this.$cookies.get("user").name}}</el-menu-item>
        <el-menu-item index="2" class="el-icon-edit">  My Account</el-menu-item>
        <el-menu-item index="3" class="el-icon-user-solid">  Information</el-menu-item>
      </el-menu>
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu class="el-menu-demo"
                 mode="vertical" background-color="#545c64"
                 text-color="#fff"
                 active-text-color="#ffd04b">
          <el-menu-item index="1">  <router-link to="/index/CourseSelection">Course Selection</router-link></el-menu-item>
          <el-menu-item index="2">  <router-link to="/index/MyClasses">My Classes</router-link></el-menu-item>
          <el-menu-item index="3">TimeTable</el-menu-item>
          <el-menu-item index="4">Semester</el-menu-item>
          <el-menu-item index="5">School Info</el-menu-item>
        </el-menu>
      </el-aside>
      <el-container>
        <router-view/>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
export default {
  name: "Index",
  data(){
    return {
      a1:'1',
      a2:'2'
    }
  }
}
</script>

<style scoped>

.el-header {
  background-color: #545c64;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #545c64;
  color: #333;
  text-align: center;
  width: 200px;
  line-height: 800px;
  margin-top: 7px;
}


body > .el-container {
  margin-top: 20px;
  margin-bottom: 40px;
}

.router-link-active {
  text-decoration: none;
  color: #ffd04b;
}
a {
  text-decoration: none;
  color: #fff;
}

</style>