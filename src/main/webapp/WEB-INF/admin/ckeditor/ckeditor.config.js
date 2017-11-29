CKEDITOR.editorConfig = function( config ){
	config.skin = 'kama',
    config.language = 'zh-cn';
    config.toolbar  = "fawtoyota";//设置工具条
    config.height = '300px';
    config.uiColor = '#E6E6F2'; // 背景颜色   E6E6F2
    
    //自定义插件
    config.extraPlugins = "uploadimage,lineheight";//上传图片、行距
    
    //从word粘贴内容时是否自动去除格式(false:否,true:是,默认true)
    config.pasteFromWordRemoveStyles = false;  
	config.pasteFromWordRemoveFontStyles = false; 
	
	//禁用自动拼写检查功能，加快装载速度
	config.disableNativeSpellChecker = false ;  
    config.scayt_autoStartup = false;
	
	//自定义设置可选字体
	config.font_names='宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;Arial/Arial';
    //设置默认字号为14px
	config.fontSize_defaultLabel = '14px';
	config.fontSize_style =
	{
		element		: 'span',
		styles		: { 'font-size' : '#(size)' },
		overrides	: [ { element : 'font', attributes : { 'size' : null } } ]
	};
    
	//common 编辑器工具条
	config.toolbar_fawtoyota = [
		{ name: 'document',		items : [ 'Source','-','Preview','-','Templates' ] },
		{ name: 'clipboard',	items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
		{ name: 'editing',		items : [ 'Find','Replace','-','SelectAll'] },
		{ name: 'basicstyles',	items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript' ] },
		'/',
		{ name: 'paragraph',	items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote','CreateDiv' ]},
		{ name: 'typesetting',	items :	[ 'JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'] },
		{ name: 'links',		items : [ 'Anchor' ] },
		{ name: 'insert',		items : [ 'HorizontalRule','Smiley','SpecialChar','PageBreak','-','Maximize', 'ShowBlocks','-','RemoveFormat' ] },
		'/',
		{ name: 'styles',		items : [ 'Styles','Format','Font','FontSize','lineheight' ] },
		{ name: 'colors',		items : [ 'TextColor','BGColor' ] },
		{ name: 'tools',		items : [ 'uploadimage','Table','-','Preview' ] }
	];
}