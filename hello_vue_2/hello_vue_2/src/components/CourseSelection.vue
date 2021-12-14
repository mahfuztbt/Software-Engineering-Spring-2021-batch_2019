<template>
  <el-table :data="loadData" border>
    <el-table-column
        fixed
        prop="id"
        label="Class ID"
        width="150">
    </el-table-column>
    <el-table-column
        prop="course.name"
        label="Course Name"
        width="250">
    </el-table-column>
    <el-table-column
        prop="course.credit"
        label="Course Credit"
        width="150">
    </el-table-column>
    <el-table-column
        prop="room"
        label="Room"
        width="120">
    </el-table-column>
    <el-table-column
        prop="day"
        label="Day Time"
        width="120">
    </el-table-column>
    <el-table-column
        prop="dayTime"
        label="Class time"
        width="120">
    </el-table-column>
    <el-table-column
        prop="teacher.name"
        label="Teacher"
        width="120">
    </el-table-column>
    <el-table-column
        prop="capacity"
        label="Capacity"
        width="120">
    </el-table-column>
    <el-table-column
        fixed="right"
        label="Operation"
        width="250">
      <template slot-scope="scope">
        <el-button @click="handleClick(scope.row)" type="info"><i class="el-icon-zoom-in"></i>view</el-button>
        <el-button @click="takeClass(scope.row)" type="danger"><i class="el-icon-edit"></i>select</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import axios from "axios"

export default {
  name: "CourseSelection",
  methods: {
    handleClick(row) {
      this.$alert(row, "info");
    },
    takeClass(row) {
      axios.get("http://localhost:9853/api/v1/Student/takeClass",
          {
            params: {studentid: this.$cookies.get("user").id, clsid: row.id}
          }).then(response => {
        this.$alert(response.data, "info");
      })
    }
  },
   mounted() {
    axios.get("http://localhost:9853/api/v1/TClass/all").then(response => {
      console.log(response.data);
      this.loadData = response.data;
    })
  },
  data() {
    return {
      loadData: []
    };
  }
}
</script>

<style scoped>

</style>