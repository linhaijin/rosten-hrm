<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<style type="text/css">
	
</style>
<script type="text/javascript">	

</script>
</head>
<body>
	<div class="searchtab">
      <table width="100%" border="0">
        
        <tbody>
          <tr>
      
            <th width="5%">申请人</th>
            <td width="18%">
            	<input id="s_chinaName"  data-dojo-type="dijit/form/ValidationTextBox" 
                	data-dojo-props='trim:true
               '/>
            </td>
            
            <th width="10%">调入部门</th>
            <td width="18%">
            	<input id="s_inDepart" data-dojo-type="dijit/form/ValidationTextBox"
	                data-dojo-props='trim:true,value:""
	            ' />
            </td>
            
             <th width="10%">调出部门</th>
            <td width="18%">
            	<input id="s_departName" data-dojo-type="dijit/form/ValidationTextBox"
	                data-dojo-props='trim:true,value:""
	            ' />
            </td>
            
            <td>
            	<div class="btn">
                	<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){departChange_search()}'>查询</button>
                	<button data-dojo-type="dijit/form/Button" data-dojo-props='onClick:function(){departChange_resetSearch()}'>重置条件</button>
              	</div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
	
</body>
</html>
