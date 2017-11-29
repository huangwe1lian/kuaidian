(function () {
     var a = {
         exec: function (editor) {
        	 showUploadDialog(editor);
         }
     },
     b = 'uploadimage';
     CKEDITOR.plugins.add(b, {
         init: function (editor) {
             editor.addCommand(b, a);
             editor.ui.addButton('uploadimage', {
                 label: '图片上传',
                 icon: this.path + 'upimg.gif',
                 command: b
             });
         }
     });
 })();