webpackJsonp([30],{C3VP:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var n=s("Xxa5"),a=s.n(n),d=s("exGp"),u=s.n(d),r=s("gyMJ"),i=s("lbHh"),c=s.n(i),l={name:"boby",data:function(){return{studentId:parseInt(this.$store.state.student.studentId),query:{openId:this.$store.state.wx.openId},studentList:[]}},methods:{handleChangeStudent:function(t){t&&(c.a.set("studentId",t.studentId),c.a.set("className",t.className),c.a.set("classId",t.classId),this.$store.commit("student/SET_STUDENTID",t.studentId),this.$store.commit("users/SET_CLASSNAME",t.className),this.$store.commit("users/SET_CLASSID",t.classId),this.$store.dispatch("queryClass/queryStudentClass",{id:this.$store.state.users.id,studentId:t.studentId}),this.$router.go(-1))},queryAllStudent:function(){var t=this,e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return u()(a.a.mark(function s(){var n;return a.a.wrap(function(s){for(;;)switch(s.prev=s.next){case 0:return s.next=2,r.a.queryAllStudent(e);case 2:0===(n=s.sent).errorCode&&(t.studentList=n.data);case 4:case"end":return s.stop()}},s,t)}))()}},mounted:function(){this.queryAllStudent(this.query)}},o={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"page"},[s("div",{staticClass:"page-bd"},[s("div",{staticClass:"cells"},t._l(t.studentList,function(e){return s("div",{key:e.studentId,staticClass:"cell student-box",on:{click:function(s){t.handleChangeStudent(e)}}},[s("div",{staticClass:"cell-hd"}),t._v(" "),s("div",{staticClass:"cell-bd"},[s("p",[t._v(t._s(e.studentName)+"("+t._s(e.className)+")")])]),t._v(" "),s("div",{staticClass:"cell-ft"},[s("van-radio-group",{model:{value:t.studentId,callback:function(e){t.studentId=e},expression:"studentId"}},[s("van-radio",{attrs:{name:e.studentId,"checked-color":"#92cd36"}})],1)],1)])}))])])},staticRenderFns:[]};var h=s("VU/8")(l,o,!1,function(t){s("HBit")},"data-v-4dfeba89",null);e.default=h.exports},HBit:function(t,e){}});