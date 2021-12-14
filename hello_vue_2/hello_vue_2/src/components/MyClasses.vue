<template>
  <el-table  :data="loadData"  border>
    <el-table-column
        fixed
        prop="tclass.id"
        label="Class ID"
        width="150">
    </el-table-column>
    <el-table-column
        prop="tclass.course.name"
        label="Course Name"
        width="250">
    </el-table-column>
    <el-table-column
        prop="tclass.course.credit"
        label="Course Credit"
        width="150">
    </el-table-column>
    <el-table-column
        prop="tclass.room"
        label="Room"
        width="120">
    </el-table-column>
    <el-table-column
        prop="tclass.day"
        label="Day Time"
        width="120">
    </el-table-column>
    <el-table-column
        prop="tclass.dayTime"
        label="Class time"
        width="120">
    </el-table-column>
    <el-table-column
        prop="tclass.teacher.name"
        label="Teacher"
        width="120">
    </el-table-column>
    <el-table-column
        prop="tclass.capacity"
        label="Capacity"
        width="120">
    </el-table-column>
    <el-table-column
        fixed="right"
        label="Operation"
        width="250">
      <template slot-scope="scope">
        <el-button @click="handleClick(scope.row)"  type="info"><i class="el-icon-zoom-in"></i>view</el-button>
        <el-button @click="withDrawClass(scope.$index,scope.row)" type="danger"><i class="el-icon-edit" ></i>withDraw</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
import axios from "axios"
export default {
  name: "MyClasses",
  methods: {
    handleClick(row) {
      this.$alert(row, "info");
    },
    withDrawClass(index,row) {
      axios.get("http://localhost:9853/api/v1/Student/withDraw",
          {
            params: {studentid: this.$cookies.get("user").id, clsid: row.tclass.id}
          }).then(response => {
        this.$alert(response.data, "info");
        this.loadData.splice(index,1);
      })
    }
  },
  mounted() {
    axios.get("http://localhost:9853/api/v1/Student/"+this.$cookies.get("user").id).then(response =>{
      this.loadData = response.data.selections;
    })
  },
  data() {
    return {
      loadData:[]
    };
  }
}
</script>

<style scoped>

</style>