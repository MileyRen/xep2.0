package com.xeq.file.action;
import java.util.ArrayList;
import java.util.List;


import com.google.gson.Gson;
import com.xeq.file.domain.FringeNode;
import com.xeq.file.domain.LinkDataArray;
import com.xeq.file.domain.NodeDataArray;
import com.xeq.file.domain.ScriptState;

public class ConvertToJSON {
	
	public static String convert(List<ScriptState> list){

		String preFixStr = "{"+"\""+"class"+"\""+":"+"\""+"go.GraphLinksModel"+"\""+","
						   +"\""+"linkFromPortIdProperty"+"\""+":"+"\""+"fromPort"+"\""+","
						   +"\""+"linkToPortIdProperty"+"\""+":"+"\""+"toPort"+"\""+",";
//		System.out.println(preFixStr);
		
		StringBuilder sBuilder = new StringBuilder(preFixStr);
		int count =0;
		count = list.size();
		NodeDataArray[] nodes = new NodeDataArray[count];//中间节点数组
		FringeNode[] fringeNodes = new FringeNode[2];//开始节点和终止节点
			
			
		if(count>0){//一个流程超过2个步骤
//			NodeDataArray[] nodes = new NodeDataArray[count-2];//中间节点数组
//			FringeNode[] fringeNodes = new FringeNode[2];//开始节点和终止节点
			//开始节点
			fringeNodes[0] = new FringeNode();
			fringeNodes[0].setCategory("Start");
			fringeNodes[0].setKey(-1);
			fringeNodes[0].setLoc("175 20");
			fringeNodes[0].setText("Start");

			int i =0;
			for(i=0; i<count; i++){
				nodes[i] = new NodeDataArray();//先实例化每个对象
				//设置Key
				nodes[i].setKey(i);
				//设置Loc
				int tempXLoc = 175 + (i+1)*120;
				int tempYLoc = 20;
				String tempLoc = String.valueOf(tempXLoc)+" "+String.valueOf(tempYLoc);
				nodes[i].setLoc(tempLoc);
				//设置Text
				boolean isFinished = false;
				if("finish".equals(list.get(i).getState())){
					isFinished = true;
					nodes[i].setText("步骤"+(i+1)+"已完成");
				}else{
					nodes[i].setText("步骤"+(i+1)+"未完成");
				}
			}
			
			//终止节点
			String finalNodeLoc = nodes[count-1].getLoc();
			String[] temp = finalNodeLoc.split(" ");
//			System.out.println(temp);
			int finalNodeXLoc = Integer.parseInt(temp[0]);
			finalNodeXLoc +=120;
			
			fringeNodes[1] = new FringeNode();
			fringeNodes[1].setCategory("End");
			fringeNodes[1].setKey(-2);
//			fringeNodes[1].setLoc("1200 20");
			fringeNodes[1].setLoc(String.valueOf(finalNodeXLoc)+" "+"20");//????是否正确
			fringeNodes[1].setText("End");
		}else if(count == 0){//只有两个节点
			//开始节点
			fringeNodes[0] = new FringeNode();
			fringeNodes[0].setCategory("Start");
			fringeNodes[0].setKey(-1);
			fringeNodes[0].setLoc("175 20");
			fringeNodes[0].setText("Start");
			//终止节点
			fringeNodes[1] = new FringeNode();
			fringeNodes[1].setCategory("End");
			fringeNodes[1].setKey(-2);
			fringeNodes[1].setLoc("250 20");
			fringeNodes[1].setText("End");
		}
		
		sBuilder.append("\""+"nodeDataArray"+"\""+":"+"[");
		//使用Gson工具对nodeData字符串进行解析
		Gson gson = new Gson();
			//解析开始节点
		sBuilder = sBuilder.append(gson.toJson(fringeNodes[0]));
		sBuilder = sBuilder.append(",");
			//解析中间节点
		for(int i=0; i<nodes.length; i++){
			sBuilder = sBuilder.append(gson.toJson(nodes[i]));
			sBuilder = sBuilder.append(",");
		}
			//解析结束节点
		sBuilder = sBuilder.append(gson.toJson(fringeNodes[1]));
		sBuilder.append("],  ");
		//测试节点数据结果
//		System.out.println(sBuilder.toString());
		
		
		//处理linkData
		 //1.首先只考虑线性连接情况
		int  edgeNums = list.size()+1;
		LinkDataArray[] linkDataArray = new LinkDataArray[edgeNums];
		boolean isFirst = false, isLast = false;
		for(int i=0; i<edgeNums; i++){
			linkDataArray[i] = new LinkDataArray(); //先实例化每个对象
			if(i==0){
				isFirst = true;
			}
			if(i==edgeNums-1){
				isLast = true;
			}
			if(isFirst == true && isLast==false){ //跟开始点相连的线
				linkDataArray[0].setFrom(-1);
				linkDataArray[0].setTo(i);
				linkDataArray[0].setFromPort("R");
				linkDataArray[0].setToPort("L");
				isFirst = false; 
			}else if(isFirst==false && isLast==false){
				linkDataArray[i].setFrom(i-1);
				linkDataArray[i].setTo(i);
				linkDataArray[i].setFromPort("R");
				linkDataArray[i].setToPort("L");
			}else if(isFirst==false && isLast ==true){//跟终止点相连的线
				linkDataArray[i].setFrom(i-1);
				linkDataArray[i].setTo(-2);
				linkDataArray[i].setFromPort("R");
				linkDataArray[i].setToPort("L");
			}else if(isFirst==true && isLast==true){ //只有开始和结束
				linkDataArray[i].setFrom(-1);
				linkDataArray[i].setTo(-2);
				linkDataArray[i].setFromPort("R");
				linkDataArray[i].setToPort("L");
			}
		}
		
		sBuilder.append("\""+"linkDataArray"+"\""+" : "+"[");
		//使用Gson工具对linkData字符串进行解析
		for(int i=0; i<edgeNums; i++){
		sBuilder = sBuilder.append(gson.toJson(linkDataArray[i]));
		if(i!= (edgeNums-1))
		 sBuilder.append(",");
		}
		sBuilder.append("]}");
		
		//测试最终结果
//		System.out.println(sBuilder.toString());
		
		return sBuilder.toString();
	}
//	public static void main(String[] args) {
////		convert();
//		List<ScriptState> list = new ArrayList<ScriptState>();
//	    list = ConnectionUtils.getQueryResult(42);
//	    System.out.println("个数:"+list.size());
//	    System.out.println(list);
//	    convert(list);
//	}

}
