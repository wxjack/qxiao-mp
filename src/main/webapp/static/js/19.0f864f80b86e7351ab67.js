webpackJsonp([19],{"1uhT":function(e,t){},VU2c:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var r=a("Xxa5"),s=a.n(r),n=a("woOf"),l=a.n(n),o=a("exGp"),c=a.n(o),i=a("Dd8w"),u=a.n(i),d=a("gyMJ"),v=a("wEM1"),p=a("NYxO"),f={name:"userEditor",mixins:[v.c,v.a,v.b],data:function(){return{leaderInfo:{},teacherInfo:{},patroarch:{},openId:this.$store.state.wx.openId}},computed:u()({},Object(p.b)("users",{roleType:function(e){return e.roleType}}),Object(p.b)("student",{studentId:function(e){return e.studentId}})),methods:{handleSubmit:function(e){switch(e){case 1:this.updateSchool();break;case 2:this.teacherInfoUpdate();break;case 3:this.studentInfoUpdate()}},studentInfoUpdate:function(){var e=this;return c()(s.a.mark(function t(){var a,r,n,o,c,i;return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(a=e.patroarch,r=a.studentName,n=a.sex,o=a.relation,c=a.studentId,""!=r){t.next=5;break}e.$toast("请完善学生名称"),t.next=10;break;case 5:return i=l()({},{studentName:r,sex:n,relation:o,openId:e.openId,studentId:c}),t.next=8,d.a.studentInfoUpdate(i);case 8:0===t.sent.errorCode&&e.$router.go(-1);case 10:case"end":return t.stop()}},t,e)}))()},teacherInfoUpdate:function(){var e=this;return c()(s.a.mark(function t(){var a,r,n,o;return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(a=e.teacherInfo,r=a.teacherName,n=a.sex,""!=r){t.next=5;break}e.$toast("请完善老师名称"),t.next=10;break;case 5:return o=l()({},{teacherName:r,sex:n,openId:e.openId}),t.next=8,d.a.teacherInfoUpdate(o);case 8:0===t.sent.errorCode&&e.$router.go(-1);case 10:case"end":return t.stop()}},t,e)}))()},updateSchool:function(){var e=this;arguments.length>0&&void 0!==arguments[0]&&arguments[0];return c()(s.a.mark(function t(){var a,r,n,o,c,i;return s.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:if(a=e.leaderInfo,r=a.schoolName,n=a.location,o=a.leaderName,c=a.type,""!=r){t.next=4;break}return e.$toast("请完善学校名称"),t.abrupt("return");case 4:if(""!=n){t.next=7;break}return e.$toast("详细地址"),t.abrupt("return");case 7:if(""!=o){t.next=10;break}return e.$toast("请完善园长名称"),t.abrupt("return");case 10:return i=l()({},{schoolName:r,location:n,leaderName:o,type:c,openId:e.openId}),t.next=13,d.a.updateSchool(i);case 13:0===t.sent.errorCode&&e.$router.go(-1);case 15:case"end":return t.stop()}},t,e)}))()},studentQueryMe:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return c()(s.a.mark(function a(){var r;return s.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,d.a.studentQueryMe(t);case 2:0===(r=a.sent).errorCode&&(e.patroarch=r.data);case 4:case"end":return a.stop()}},a,e)}))()},queryTeacherInfo:function(e){var t=this;return c()(s.a.mark(function a(){var r;return s.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,d.a.queryTeacherInfo({openId:e});case 2:0===(r=a.sent).errorCode&&(t.teacherInfo=r.data);case 4:case"end":return a.stop()}},a,t)}))()},queryInfo:function(e){var t=this;return c()(s.a.mark(function a(){var r;return s.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,d.a.queryInfo({openId:e});case 2:0===(r=a.sent).errorCode&&(t.leaderInfo=r.data);case 4:case"end":return a.stop()}},a,t)}))()}},mounted:function(){1==this.roleType||4==this.roleType?this.queryInfo(this.openId):2==this.roleType?this.queryTeacherInfo(this.openId):this.studentQueryMe({openId:this.openId,studentId:this.studentId})}},h={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"page"},[a("div",{staticClass:"page-bd"},[a("div",{staticClass:"cells-title"},[e._v("基础信息修改")]),e._v(" "),1==e.roleType||4==e.roleType?[a("div",{staticClass:"cells"},[a("div",{staticClass:"cell"},[e._m(0),e._v(" "),a("div",{staticClass:"cell-bd"},[a("input",{directives:[{name:"model",rawName:"v-model",value:e.leaderInfo.leaderName,expression:"leaderInfo.leaderName"}],staticClass:"input",attrs:{placeholder:"请输入园长名称",maxlength:"4"},domProps:{value:e.leaderInfo.leaderName},on:{input:function(t){t.target.composing||e.$set(e.leaderInfo,"leaderName",t.target.value)}}})])]),e._v(" "),a("div",{staticClass:"cell"},[e._m(1),e._v(" "),a("div",{staticClass:"cell-bd"},[a("input",{directives:[{name:"model",rawName:"v-model",value:e.leaderInfo.schoolName,expression:"leaderInfo.schoolName"}],staticClass:"input",attrs:{placeholder:"请输入学校名称"},domProps:{value:e.leaderInfo.schoolName},on:{input:function(t){t.target.composing||e.$set(e.leaderInfo,"schoolName",t.target.value)}}})])]),e._v(" "),a("div",{staticClass:"cell"},[e._m(2),e._v(" "),a("div",{staticClass:"cell-bd"},[a("input",{directives:[{name:"model",rawName:"v-model",value:e.leaderInfo.location,expression:"leaderInfo.location"}],staticClass:"input",attrs:{placeholder:"请输入详细地址"},domProps:{value:e.leaderInfo.location},on:{input:function(t){t.target.composing||e.$set(e.leaderInfo,"location",t.target.value)}}})])])]),e._v(" "),a("div",{staticClass:"btn-group"},[a("a",{staticClass:"btn btn-large btn-primary",attrs:{href:"javascript:void(0);"},on:{click:function(t){e.handleSubmit(1)}}},[e._v("保存")])])]:e._e(),e._v(" "),2==e.roleType?[a("div",{staticClass:"cells"},[a("div",{staticClass:"cell"},[e._m(3),e._v(" "),a("div",{staticClass:"cell-bd"},[a("input",{directives:[{name:"model",rawName:"v-model",value:e.teacherInfo.teacherName,expression:"teacherInfo.teacherName"}],staticClass:"input",attrs:{placeholder:"请输入老师姓名",maxlength:"4"},domProps:{value:e.teacherInfo.teacherName},on:{input:function(t){t.target.composing||e.$set(e.teacherInfo,"teacherName",t.target.value)}}})])]),e._v(" "),a("div",{staticClass:"cell cell-select cell-select-after"},[e._m(4),e._v(" "),a("div",{staticClass:"cell-bd"},[a("select",{directives:[{name:"model",rawName:"v-model",value:e.teacherInfo.sex,expression:"teacherInfo.sex"}],staticClass:"select",attrs:{name:"",dir:"rtl"},on:{change:function(t){var a=Array.prototype.filter.call(t.target.options,function(e){return e.selected}).map(function(e){return"_value"in e?e._value:e.value});e.$set(e.teacherInfo,"sex",t.target.multiple?a:a[0])}}},e._l(e.sexList,function(t,r){return a("option",{key:r,domProps:{value:t.id}},[e._v(e._s(t.name))])}))])])]),e._v(" "),a("div",{staticClass:"btn-group"},[a("a",{staticClass:"btn btn-large btn-primary",attrs:{href:"javascript:void(0);"},on:{click:function(t){e.handleSubmit(2)}}},[e._v("保存")])])]:e._e(),e._v(" "),3==e.roleType?[a("div",{staticClass:"cells"},[a("div",{staticClass:"cell"},[e._m(5),e._v(" "),a("div",{staticClass:"cell-bd"},[a("input",{directives:[{name:"model",rawName:"v-model",value:e.patroarch.studentName,expression:"patroarch.studentName"}],staticClass:"input",attrs:{placeholder:"请输入学生姓名",maxlength:"4"},domProps:{value:e.patroarch.studentName},on:{input:function(t){t.target.composing||e.$set(e.patroarch,"studentName",t.target.value)}}})])]),e._v(" "),a("div",{staticClass:"cell cell-select cell-select-after"},[e._m(6),e._v(" "),a("div",{staticClass:"cell-bd"},[a("select",{directives:[{name:"model",rawName:"v-model",value:e.patroarch.sex,expression:"patroarch.sex"}],staticClass:"select",attrs:{name:"",dir:"rtl"},on:{change:function(t){var a=Array.prototype.filter.call(t.target.options,function(e){return e.selected}).map(function(e){return"_value"in e?e._value:e.value});e.$set(e.patroarch,"sex",t.target.multiple?a:a[0])}}},e._l(e.sexList,function(t,r){return a("option",{key:r,domProps:{value:t.id}},[e._v(e._s(t.name))])}))])]),e._v(" "),a("div",{staticClass:"cell cell-select cell-select-after"},[e._m(7),e._v(" "),a("div",{staticClass:"cell-bd"},[a("select",{directives:[{name:"model",rawName:"v-model",value:e.patroarch.relation,expression:"patroarch.relation"}],staticClass:"select",attrs:{name:"",dir:"rtl"},on:{change:function(t){var a=Array.prototype.filter.call(t.target.options,function(e){return e.selected}).map(function(e){return"_value"in e?e._value:e.value});e.$set(e.patroarch,"relation",t.target.multiple?a:a[0])}}},e._l(e.relationList,function(t,r){return a("option",{key:r,domProps:{value:t.id}},[e._v(e._s(t.name))])}))])])]),e._v(" "),a("div",{staticClass:"btn-group"},[a("a",{staticClass:"btn btn-large btn-primary",attrs:{href:"javascript:void(0);"},on:{click:function(t){e.handleSubmit(3)}}},[e._v("保存")])])]:e._e()],2)])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"cell-hd"},[t("label",{staticClass:"label",attrs:{for:""}},[this._v("姓名")])])},function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"cell-hd"},[t("label",{staticClass:"label",attrs:{for:""}},[this._v("学校名称")])])},function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"cell-hd"},[t("label",{staticClass:"label",attrs:{for:""}},[this._v("详细地址")])])},function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"cell-hd"},[t("label",{staticClass:"label",attrs:{for:""}},[this._v("姓名")])])},function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"cell-hd"},[t("label",{staticClass:"label",attrs:{for:""}},[this._v("性别")])])},function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"cell-hd"},[t("label",{staticClass:"label",attrs:{for:""}},[this._v("学生姓名")])])},function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"cell-hd"},[t("label",{staticClass:"label",attrs:{for:""}},[this._v("性别")])])},function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"cell-hd"},[t("label",{staticClass:"label",attrs:{for:""}},[this._v("学生和家长关系")])])}]};var m=a("VU/8")(f,h,!1,function(e){a("1uhT")},"data-v-ac459cd6",null);t.default=m.exports}});