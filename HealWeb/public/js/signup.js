
function validate() {
    var ip=document.getElementsByTagName('input');
    var values=[];
    var counter=0;
    for(var i=0;i<ip.length;i++)
    {
        values.push(ip[i].value);
        if(values[i]=="")
        {
            ip[i].setAttribute('class','input-field red');
            counter++;
        }
    }
    
    if (counter!=0) {
        alert("Fill all feilds !");
    }
    else{
        document.getElementById('completeProfile').submit();
    }
    console.log(values);    
}