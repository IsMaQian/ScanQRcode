<?php 	

require 'conorcal.php';

$droneid = $_POST['droneid'];

$result1=array();

if (!empty($_POST['droneid'])) {
	# code...
	$query = 'SELECT * FROM AYDRONE_AIR where drone_id= :droneid'; 
	$stid = oci_parse($conn, $query) ;// 编译SQL语句，准备执行 

	oci_bind_by_name($stid, ":droneid", $droneid);//设置绑定变量的取值
    
    }else {
        # code...
        $query = 'SELECT * FROM AYDRONE_AIR '; // 查询语句 
        $stid = oci_parse($conn, $query) ;
    }

//$stid = oci_parse($conn, $query) ;// 编译SQL语句，准备执行 
if (!$stid) { 
$e = oci_error($conn); 
echo($e['message']); 
exit; 
} 


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

if(isset($result1)){
	echo json_encode($result1);
	
}
oci_free_statement($stid);  //释放关联于语句或游标的所有资源
oci_close($conn);   //关闭 Oracle数据库连接  


 ?>