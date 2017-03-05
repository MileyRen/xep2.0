!function(){
	laydate.skin('danlan');//�л�Ƥ������鿴skins����Ƥ����
	laydate({elem: '#demo'});//��Ԫ��
}();
//���ڷ�Χ����
var start = {
    elem: '#start',
    format: 'YYYY-MM-DD',
    min: laydate.now(), //�趨��С����Ϊ��ǰ����
    max: '2099-06-16', //�������
    istime: true,
    istoday: false,
    choose: function(datas){
         end.min = datas; //��ʼ��ѡ�ú����ý����յ���С����
         end.start = datas //�������յĳ�ʼֵ�趨Ϊ��ʼ��
    }
};
var end = {
    elem: '#end',
    format: 'YYYY-MM-DD',
    min: laydate.now(),
    max: '2099-06-16',
    istime: true,
    istoday: false,
    choose: function(datas){
        start.max = datas; //������ѡ�ú󣬳�ֵ��ʼ�յ��������
    }
};
laydate(start);
laydate(end);

//�Զ������ڸ�ʽ
 laydate({
    elem: '#test1',
    format: 'YYYY��MM��DD��',
    festival: true, //��ʾ����
    choose: function(datas){ //ѡ��������ϵĻص�
        alert('�õ���'+datas);
    }
}); 

//���ڷ�Χ�޶������쵽����
 laydate({
    elem: '#hello3',
    min: laydate.now(-1), //-1�������죬-2����ǰ�죬�Դ�����
    max: laydate.now(+1) //+1�������죬+2������죬�Դ�����
}); 
