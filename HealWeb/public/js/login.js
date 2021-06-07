
function validate() {
    var ip=document.getElementsByTagName('input');
    var id=ip[0].value;
    var pass=ip[1].value;
    for(var i=0;i<data.length;i++)
    {
        if(id==data[i][0])
        {
            
            console.log(pass);
            if (pass==data[i][1]) {
                document.getElementById('loginform').submit();
                console.log("succesful login");
                break;
            }
            else
            {
                document.getElementById('result').innerHTML='Wrong Credentials';
                console.log("Wrong password");
            }
        }
        else
        {
            document.getElementById('result').innerHTML='Wrong Credentials';
            console.log("Wrong username");
        }
    }
}
