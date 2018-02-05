<?php 	

require 'conorcal.php';

$query = 'SELECT * FROM AYDRONE_AIR where drone_id=:id1'; // 查询语句 
$stid = oci_parse($conn, $query); // 编译SQL语句，准备执行 
if (!$stid) { 
$e = oci_error($conn); 
echo($e['message']); 
exit; 
} 

//有查询条件的查询
$id1="S1000004";//设置绑定变量的取值
oci_bind_by_name($stid, ":id1", $id1);

$r = oci_execute($stid);

//$r = oci_execute($stid, OCI_DEFAULT); // 执行SQL。OCI_DEFAULT表示不要自动commit 
if(!$r) { 
$e = oci_error($stid); 
echo ($e['message']); 
exit; 
} 


while ($row = oci_fetch_assoc($stid)) { //提取结果数据的一行到一个关联数组  
$result1[] = $row;
} 

if($result1){
	
	echo json_encode($result1);
}else{
	# code...
    echo "无数据";
}

oci_free_statement($stid);  //释放关联于语句或游标的所有资源
oci_close($conn);   //关闭 Oracle数据库连接  


 ?>