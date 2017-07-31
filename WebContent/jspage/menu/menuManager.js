
$(function(){
	  var ctxPath = $("#ctxPath").val();
			var windowHeight=document.body.scrollHeight*0.9;
			var windowWidth=document.body.scrollWidth*0.9; 
			$('#test').treegrid({
				title:'',
				width:windowWidth,
				height:windowHeight,
				nowrap: false,
				rownumbers: true,
				animate:true,
				collapsible:false,
				url:ctxPath + '/menuManage/selectMenuJson',
				idField:'menuNum',
				treeField:'menuNum',
				frozenColumns:[[
		            {title:'菜单编号',field:'menuNum',width:150,
		                formatter:function(value){
		                	return '<span style="color:red">'+value+'</span>';
		                }
		            }
				]],
				columns:[[
					{field:'menuName',title:'菜单名称',width:150},
					{field:'menuImge',title:'菜单图标',width:200},
					{field:'parentMenuNum',title:'父菜单编号',width:150},
					{field:'menuURL',title:'入口路径',width:260},
					{field:'describe',title:'菜单功能描述',width:260}
					
				]],
				onBeforeLoad:function(row,param){
					if (row){
						$(this).treegrid('options').url = ctxPath + '/menuManage/selectMenuJson';
					} else {
						$(this).treegrid('options').url = ctxPath + '/menuManage/selectMenuJson';
					}
				},
				onContextMenu: function(e,row){
					e.preventDefault();
					$(this).treegrid('unselectAll');
					$(this).treegrid('select', row.code);
					$('#mm').menu('show', {
						left: e.pageX,
						top: e.pageY
					});
				}
			});
		});

	
	
		function deleteMenuInfo(){
			  var ctxPath = $("#ctxPath").val();
			var node = $('#test').treegrid('getSelected');
			if (node){
				var nodes = $('#test').treegrid('getChildren', node.menuNum);
				if(nodes==''){
					$.messager.confirm('提示', '确定删除该菜单信息吗？', function(r){
						if (r){
							window.location.href=ctxPath + "/menuManage/subDelMenu?pk_id="+node.pk_id;
							//sayInfo("删除成功！");
						}
					});
					
				}else{
					sayInfo('删除父菜单前，请先删除或转移子菜单。');
				}
				
			} else {
				sayInfo('请先选择目标菜单！');
			}
		}
		

		function editSub(){
			
			var node = $('#test').treegrid('getSelected');
			
			if($.trim($('#menuName').val())==''){
				sayInfo('菜单名称不能为空！');
				return;
			}
			
			 if($.trim($('#menuNum').val())==''){
				sayInfo('菜单图片不能为空！');
				return;
			} 

			document.editForm.submit();
			//sayInfo('修改成功。');
		}
		
		function addSub(){
			
			if($.trim($('#menuName_add').val())==''){
				sayInfo('菜单名称不能为空！');
				return;
			}
			
			 if($.trim($('#menuNum_add').val())==''){
				sayInfo('菜单编号不能为空！');
				return;
			} 
			
			document.addForm.submit();
			//sayInfo('成功添加新菜单。');
		}

function editMenuInfo(){
		var menu = $('#test').treegrid('getSelected');
		if (menu){
			//sayInfo('已经选择目标菜单！');
			openEditDialog(menu);
			//sayInfo('已经选择目标菜单！');
			
		} else {
			sayInfo('请先选择目标菜单！');
		}
	}
function openEditDialog(menu){
	//alert(menu.describe);
	$('#menuName').attr("value",menu.menuName);
	$('#menuImge').attr("value",menu.menuImge);
	$('#menuNum').attr("value",menu.menuNum);
	$('#parentMenuNum').attr("value",menu.parentMenuNum);
	$('#menuURL').attr("value",menu.menuURL);
	$('#describe').attr("value",menu.describe);
	
	$('#editMenu').dialog('open');
}

function openAddDialog(){
	$('#addMenu').dialog('open');
}

//取消按钮事件
function cancleAddDialog(){
	$('#addMenu').dialog('close');
}

//取消按钮事件
function cancleDialog(){
	$('#editMenu').dialog('close');
}

function loadTree(){
/*	$('#treediv').dialog(
			{position:['300','200']}
			);*/
	$('#tree').css('display','block');
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	$("#selectAll").bind("click", selectAll);
}
/**
 * 重置表单数据
 */
function resetAddForm(){
	$('#tree').css('display','none');
	$('#departName').val("");
	$('#pname').val("");
	$('#demo').val("");
}
