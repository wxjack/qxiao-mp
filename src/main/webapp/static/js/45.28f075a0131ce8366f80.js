webpackJsonp([45],{BBVy:function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=s("Xxa5"),i=s.n(a),l=s("exGp"),n=s.n(l),r=s("woOf"),o=s.n(r),c=s("+6Bu"),d=s.n(c),u=s("Dd8w"),f=s.n(u),m=s("gyMJ"),v=s("NYxO"),p=s("MqaO"),h={name:"freshAdd",data:function(){return{serverId:[],imagesList:[],selected:[],form:{openId:this.$route.query.openId,title:"",textContent:"",images:[]}}},computed:f()({},Object(v.b)("users",{id:function(e){return e.id},roleType:function(e){return e.roleType}}),Object(v.b)("queryClass",{classList:function(e){return e.classList}})),methods:{handleChooseImage:function(){var e=this;wx.chooseImage({count:9,sizeType:["compressed"],sourceType:["album","camera"],success:function(t){var s=t.localIds;window.__wxjs_is_wkwebview?e.handleLocalImgData(s):s.forEach(function(t){e.imagesList.push(t)}),e.handleUploadImage(s)},fail:function(e){alert("失败")}})},handlePreviewImage:function(e){wx.previewImage({current:e,urls:this.imagesList})},handleUploadImage:function(e){var t=this,s=0,a=e.length;!function i(){var l=e[s];window.__wxjs_is_wkwebview&&-1!=l.indexOf("wxlocalresource")&&(l=l.replace("wxlocalresource","wxLocalResource")),wx.uploadImage({localId:l,isShowProgressTips:1,success:function(e){var l=e.serverId;t.serverId.push(l),++s<a&&i()},fail:function(e){alert("失败")}})}()},handleLocalImgData:function(e){var t=this,s=0,a=e.length;!function i(){wx.getLocalImgData({localId:e[s],success:function(e){var l=e.localData;l=l.replace("jgp","jpeg"),t.imagesList.push(l),++s<a&&i()}})}()},handleDelImg:function(e){this.imagesList.splice(e,1),this.serverId.splice(e,1)},handleSubmit:function(){var e=this,t=this.form,s=t.title,a=t.textContent;d()(t,["title","textContent"]);if(""!==s)if(""!==a||this.serverId.length)if(this.form.textContent=Object(p.a)(a)||"",this.selected.length){var i=this.selected.map(function(e){return{classId:e}}),l={openId:this.form.openId,imgIds:this.serverId},n=o()({},this.form,{senders:i});this.serverId.length?m.a.imgIds(l).then(function(t){0===t.errorCode?(n.images=t.data.paths,m.a.freshAdd(n).then(function(t){0===t.errorCode&&(e.$refs.form.reset(),e.$router.go(-1))})):alert("null")}):this.freshAdd(n)}else this.$toast("请选择发送班级");else this.$toast("请输入速报内容或者上传图片");else this.$toast("请输入速报标题")},freshAdd:function(){var e=this,t=arguments.length>0&&void 0!==arguments[0]?arguments[0]:{};return n()(i.a.mark(function s(){return i.a.wrap(function(s){for(;;)switch(s.prev=s.next){case 0:return s.next=2,m.a.freshAdd(t);case 2:0===s.sent.errorCode&&(e.$refs.form.reset(),e.$router.go(-1));case 4:case"end":return s.stop()}},s,e)}))()}},mounted:function(){}},g={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"flex-page"},[s("div",{staticClass:"flex-bd"},[s("form",{ref:"form",attrs:{action:"",method:"post"}},[s("div",{staticClass:"cells"},[s("div",{staticClass:"cell"},[s("div",{staticClass:"cell-hd"}),e._v(" "),s("div",{staticClass:"cell-bd",staticStyle:{"padding-left":"0"}},[s("input",{directives:[{name:"model",rawName:"v-model",value:e.form.title,expression:"form.title"}],staticClass:"input",staticStyle:{"text-align":"left"},attrs:{placeholder:"请输入速报标题",maxlength:"20"},domProps:{value:e.form.title},on:{input:function(t){t.target.composing||e.$set(e.form,"title",t.target.value)}}})])]),e._v(" "),s("div",{staticClass:"cell"},[s("div",{staticClass:"cell-bd",staticStyle:{"padding-left":"0"}},[s("textarea",{directives:[{name:"model",rawName:"v-model",value:e.form.textContent,expression:"form.textContent"}],staticClass:"textarea",attrs:{placeholder:"请输入速报内容...",rows:"6"},domProps:{value:e.form.textContent},on:{input:function(t){t.target.composing||e.$set(e.form,"textContent",t.target.value)}}})])]),e._v(" "),s("div",{staticClass:"cell"},[s("div",{staticClass:"cell-bd",staticStyle:{"padding-left":"0"}},[s("ul",{staticClass:"uploader-files"},e._l(e.imagesList,function(t,a){return s("li",{key:a,staticClass:"uploader-file",style:{backgroundImage:"url("+t+")"}},[s("i",{staticClass:"iconfont icon-guanbi2fill",on:{click:function(t){t.stopPropagation(),e.handleDelImg(a)}}})])})),e._v(" "),s("div",{staticClass:"uploader-input_box",on:{click:e.handleChooseImage}})])]),e._v(" "),s("div",{staticClass:"cell cell-select cell-select-after"},[e._m(0),e._v(" "),s("div",{staticClass:"cell-bd",staticStyle:{"padding-left":"0"}},[s("select",{directives:[{name:"model",rawName:"v-model",value:e.selected,expression:"selected"}],staticClass:"select",attrs:{name:"select",dir:"rtl",multiple:"",size:"1"},on:{change:function(t){var s=Array.prototype.filter.call(t.target.options,function(e){return e.selected}).map(function(e){return"_value"in e?e._value:e.value});e.selected=t.target.multiple?s:s[0]}}},[s("optgroup",{attrs:{disabled:"",hidden:""}}),e._v(" "),e._l(e.classList,function(t,a){return s("option",{key:a,domProps:{value:t.classId}},[e._v(e._s(t.className))])})],2)])])])]),e._v(" "),s("div",{staticClass:"btn-group"},[s("a",{staticClass:"btn btn-large btn-primary",attrs:{href:"javascript:void(0);"},on:{click:e.handleSubmit}},[e._v("发布")])])])])},staticRenderFns:[function(){var e=this.$createElement,t=this._self._c||e;return t("div",{staticClass:"cell-hd"},[t("label",{staticClass:"label",attrs:{for:""}},[this._v("发送班级")])])}]};var C=s("VU/8")(h,g,!1,function(e){s("cyRX")},"data-v-19a2465c",null);t.default=C.exports},cyRX:function(e,t){}});