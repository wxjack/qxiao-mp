webpackJsonp([18],{FTJl:function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var s=a("Xxa5"),o=a.n(s),r=a("exGp"),n=a.n(r),i=a("gyMJ"),u={name:"homeWork",mixins:[a("a9+M").a],data:function(){return{long:0,time:0,popupShow:!1,className:this.$route.query.className,isLoading:!1,totalPage:1,query:{openId:this.$route.query.openId,classId:this.$route.query.classId,studentId:this.$route.query.studentId,page:1,pageSize:10},roleType:this.$route.query.roleType,queryClass:{id:this.$route.query.id,roleType:this.$route.query.roleType},homeworkData:[],classList:[]}},filters:{brReplace:function(e){return e?e.replace(/<br\/>/g,""):""}},methods:{onClose:function(e,t){var a=this;return function(s,r){switch(s){case"right":a.$dialog.confirm({title:"提示",message:"确定删除该条作业吗？"}).then(n()(o.a.mark(function s(){var r;return o.a.wrap(function(s){for(;;)switch(s.prev=s.next){case 0:return r={openId:a.query.openId,homeId:e.homeId},s.next=3,i.a.homeworkDelete(r);case 3:0===s.sent.errorCode&&a.homeworkData.splice(t,1);case 5:case"end":return s.stop()}},s,a)}))).catch(function(){r.close()})}}},go:function(e){var t=e.classId,a=e.homeId,s=e.studentId,o=e.needConfirm;this.$router.push({path:"/homework/show",query:{classId:t,homeId:a,studentId:s,needConfirm:o}})},handleClassConfirm:function(e,t){this.className=e.className,this.query.classId=e.classId,this.homeworkQuery(this.query)},handleLoadingMore:function(e){var t=this,a=0;document.documentElement&&document.documentElement.scrollTop?a=document.documentElement.scrollTop:document.body&&(a=document.body.scrollTop),document.body.offsetHeight-a-window.innerHeight<=200&&!1===this.isLoading&&this.query.page<this.totalPage&&(this.isLoading=!0,this.query.page+=1,i.a.homeworkQuery(this.query).then(function(e){if(0===e.errorCode){t.totalPage=e.data.totalPage,t.query.page=e.data.page,t.isLoading=!1;var a=e.data.data;a.length&&a.forEach(function(e){t.homeworkData.push(e)})}}))},homeworkQuery:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return n()(o.a.mark(function a(){var s;return o.a.wrap(function(a){for(;;)switch(a.prev=a.next){case 0:return a.next=2,i.a.homeworkQuery(t);case 2:0===(s=a.sent).errorCode&&(e.popupShow=!1,e.query.page=s.data.page,e.totalPage=s.data.totalPage,e.isLoading=!1,e.homeworkData=s.data.data);case 4:case"end":return a.stop()}},a,e)}))()},queryClassGroup:function(){var e=this;return n()(o.a.mark(function t(){var a,s,r,n,u;return o.a.wrap(function(t){for(;;)switch(t.prev=t.next){case 0:return a=e.$route.query,s=a.id,r=a.studentId,n=a.roleType,t.next=3,i.a.queryClassId({id:s,studentId:r,roleType:n});case 3:0===(u=t.sent).errorCode&&(e.classList=u.data);case 5:case"end":return t.stop()}},t,e)}))()}},mounted:function(){this.queryClassGroup(),this.homeworkQuery(this.query)}},c={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"page"},[1==e.roleType||2==e.roleType||4==e.roleType?[s("div",{staticClass:"page-hd"},[s("div",{staticClass:"button-sp-area flex",attrs:{"size-17":""}},[s("a",{attrs:{href:"javascript:;",id:"showDatePicker"},on:{click:function(t){e.popupShow=!0}}},[s("span",[e._v(e._s(e.className))]),e._v(" "),s("van-icon",{attrs:{name:"arrow-down",size:"16px"}})],1)])])]:e._e(),e._v(" "),s("div",{staticClass:"page-bd"},[s("van-popup",{attrs:{position:"bottom"},model:{value:e.popupShow,callback:function(t){e.popupShow=t},expression:"popupShow"}},[s("van-picker",{attrs:{columns:e.classList,"show-toolbar":"","value-key":"className"},on:{cancel:function(t){e.popupShow=!1},confirm:e.handleClassConfirm}})],1),e._v(" "),2==e.roleType?[s("router-link",{staticClass:"release",attrs:{to:"/homework/add"}},[s("van-icon",{attrs:{name:"description",size:"24px"}})],1)]:e._e(),e._v(" "),e._l(e.homeworkData,function(t,a){return s("van-swipe-cell",{key:a,ref:"swipeCell",refInFor:!0,attrs:{"right-width":60,disabled:3!==e.roleType,"on-close":e.onClose(t,a)}},[s("van-cell-group",[s("figure",{staticClass:"figure figure-skin-two",on:{click:function(a){e.go(t)}}},[s("div",{staticClass:"figure-bd"},[s("div",{staticClass:"figure-info"},[s("figcaption",{staticClass:"text-ellipsis",attrs:{"size-18":""}},[t.status?e._e():s("i"),e._v(" "),s("span",[e._v(e._s(t.title))])]),e._v(" "),s("div",{staticClass:"metedata flex"},[s("time",{staticClass:"time"},[e._v(e._s(t.postTime))])]),e._v(" "),t.topImage?s("div",{staticClass:"figure-thumb-medium",style:{backgroundImage:"url("+t.topImage+")"}}):e._e(),e._v(" "),s("p",{staticClass:"line-clamp"},[e._v(e._s(e._f("brReplace")(t.textContent)))])])]),e._v(" "),s("div",{staticClass:"figure-ft"},[s("div",{staticClass:"figure-icon"},[s("van-icon",{attrs:{name:"eye-o",size:"16px"}}),e._v(" "),s("b",[e._v(e._s(t.classReadCount))])],1)])])]),e._v(" "),s("span",{staticStyle:{"line-height":"80px"},attrs:{slot:"right"},slot:"right"},[e._v("删除")])],1)}),e._v(" "),e.homeworkData.length?e._e():s("div",{staticClass:"empty"},[s("img",{attrs:{src:a("UHh/"),alt:""}}),e._v(" "),s("p",[e._v("暂无亲子作业")])])],2)],2)},staticRenderFns:[]};var l=a("VU/8")(u,c,!1,function(e){a("LD+m")},"data-v-b13a6c52",null);t.default=l.exports},"LD+m":function(e,t){}});