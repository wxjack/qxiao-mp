webpackJsonp([32],{RwDW:function(e,a){},dae0:function(e,a,t){"use strict";Object.defineProperty(a,"__esModule",{value:!0});var s=t("gyMJ"),i={name:"albumAdd",data:function(){return{serverId:[],imagesList:[],form:{openId:this.$route.query.openId,channelId:this.$route.query.channelId,type:0,imageUrl:[],videoUrl:""},albumChannel:[]}},methods:{handleChooseImage:function(){var e=this;wx.chooseImage({count:9,sizeType:["compressed"],sourceType:["album","camera"],success:function(a){var t=a.localIds;window.__wxjs_is_wkwebview?e.handleLocalImgData(t):t.forEach(function(a){e.imagesList.push(a)}),e.handleUploadImage(t)},fail:function(e){alert("失败")}})},handlePreviewImage:function(e){wx.previewImage({current:e,urls:this.imagesList})},handleUploadImage:function(e){var a=this,t=0,s=e.length;!function i(){var n=e[t];window.__wxjs_is_wkwebview&&-1!=n.indexOf("wxlocalresource")&&(n=n.replace("wxlocalresource","wxLocalResource")),wx.uploadImage({localId:n,isShowProgressTips:1,success:function(e){var n=e.serverId;a.serverId.push(n),++t<s&&i()},fail:function(e){alert("失败")}})}()},handleLocalImgData:function(e){var a=this,t=0,s=e.length;!function i(){wx.getLocalImgData({localId:e[t],success:function(e){var n=e.localData;n=n.replace("jgp","jpeg"),a.imagesList.push(n),++t<s&&i()}})}()},handleDelImg:function(){this.imagesList.splice(index,1),this.serverId.splice(index,1)},handleSubmit:function(){var e=this;if(this.serverId.length){var a={openId:this.form.openId,imgIds:this.serverId};s.a.imgIds(a).then(function(a){0===a.errorCode&&(e.form.imageUrl=a.data.paths,s.a.albumImageAdd(e.form).then(function(a){0===a.errorCode&&(e.$refs.form.reset(),e.$router.go(-1))}))})}else this.$toast("请上传图片")}},mounted:function(){}},n={render:function(){var e=this,a=e.$createElement,t=e._self._c||a;return t("div",{staticClass:"flex-page"},[t("div",{staticClass:"flex-bd"},[t("form",{ref:"form",attrs:{action:"",method:"post"}},[t("div",{staticClass:"cells mb-20"},[t("div",{staticClass:"cell"},[t("div",{staticClass:"cell-bd",staticStyle:{"padding-left":"0"}},[t("ul",{staticClass:"uploader-files"},e._l(e.imagesList,function(a,s){return t("li",{key:s,staticClass:"uploader-file",style:{backgroundImage:"url("+a+")"}},[t("i",{staticClass:"iconfont icon-guanbi2fill",on:{click:function(a){a.stopPropagation(),e.handleDelImg(s)}}})])})),e._v(" "),t("div",{staticClass:"uploader-input_box",on:{click:e.handleChooseImage}})])])])])]),e._v(" "),t("div",{staticClass:"flex-ft"},[t("div",{staticClass:"mamba"},[t("a",{staticClass:"btn btn-large btn-primary",attrs:{href:"javascript:void(0);"},on:{click:e.handleSubmit}},[e._v("保存")])])])])},staticRenderFns:[]};var l=t("VU/8")(i,n,!1,function(e){t("RwDW")},"data-v-4663c869",null);a.default=l.exports}});