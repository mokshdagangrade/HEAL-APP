const express = require('express');
const app = express();
const bodyParser = require('body-parser');
const firebase = require('firebase');

app.use(bodyParser.urlencoded({
    extended: false
}));
app.use(bodyParser.json());
app.use('/', express.static(__dirname + "/public"));
app.set('view engine', 'ejs');

// Your web app's Firebase configuration

// Initialize Firebase
firebase.initializeApp(firebaseConfig);
var database = firebase.database();

//View Data
var data = [];

async function view(parent, child) {
    var temp = [];
    await firebase.database().ref(parent + "/" + child + "/").once('value', function (snapshot) {
        snapshot.forEach(function (childsnapshot) {
            var childkey = childsnapshot.key;
            var childdata = childsnapshot.val();
            temp.push(childdata);
        });
        data = temp;
    });
}

//Add data
function writeUserData(key) {
    var key = firebase.database().ref('Users/Hospitals/').push().key;
    firebase.database().ref('Users/Hospitals/' + key).set({
        id: "",
        password: "",
        key: key
    });
}

//update Data

var user, auth;

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/views/home.html');
});

app.get('/login', async (req, res) => {
    await view('Users', 'Hospitals');
    module.exports = data;
    res.render(__dirname + '/views/login.ejs', {
        result: ""
    });
});

var hospdata = [];

app.post('/postlogin', async (req, res) => {
    await view('Users', 'Hospitals');
    console.log(req.body);
    console.log(data);
    var id = req.body.username;
    var pass = req.body.password;

    async function validate() {
        var count = 0,auth;
        for (var i = 0; i < data.length; i++) {

            if(id=="" || pass=="")
            {
                console.log("Empty Feilds");
                res.render('login.ejs', {
                    result: "Empty feilds are invalid"
                });  
                break;
            }

            if (id == data[i].id) {
                console.log(pass);

                if (pass == data[i].password && pass!="") {
                    console.log("succesful login");
                    auth = 1;
                    await firebase.database().ref("Users/Hospitals/").once('value', function (snapshot) {
                        snapshot.forEach(function (childsnapshot) {
                            var childkey = childsnapshot.key;
                            var childdata = childsnapshot.val();
                            if (childdata.id == id) {
                                hospdata = childdata;
                            }
                        });
                        console.log("Fetching from database : ");
                        console.log(hospdata);
                    });

                    if (hospdata.Profile_Completed == "True") {
                        res.redirect('/Details-' + hospdata.id)
                        user = data[i].key;
                        break;
                    } 
                }
            }
            else{
                count++;
            }
        }
        if (count == data.length) {
            console.log("Invalid Username");
            res.render('login.ejs', {
                result: "Wrong Username"
            });
        } else if (auth != 1) {
            console.log("Invalid Password");
            res.render('login.ejs', {
                result: "Wrong password"
            });
        }
    }
    await validate();

});

app.get('/Complete-Profile-:a', (req, res) => {
    res.render('CompleteProfile.ejs', {
        username: hospdata.id
    });
});

app.post('/Details-:a', async (req, res) => {

    hospdata.Profile_Completed = "False";

    hospdata.name = req.body.hospname,
        hospdata.email = req.body.email,
        hospdata.address = req.body.address,
        hospdata.phone = req.body.ph,
        hospdata.license = req.body.license

    // Write the new post's data simultaneously in the posts list and the user's post list.
    var updates = {};
    updates['Users/Hospitals/' + hospdata.key] = hospdata;
    await firebase.database().ref().update(updates);

    res.redirect('/Details-' + hospdata.id);
});

app.get("/Details-:a", async (req, res) => {

    console.log("Get called");
    
    
    async function getDetails(){
        var DriversWorking = [];
        await firebase.database().ref("driversWorking").once('value', async function (snapshot) {
    
            await snapshot.forEach(function (childsnapshot) {
                var childkey = childsnapshot.key;
                var childdata = childsnapshot.val();
                childdata.driverkey = childkey;
                DriversWorking.push(childdata);
            });
        });
        var DriverPatient=[];
        for(var i=0;i<DriversWorking.length;i++)
        {
            var childdata=DriversWorking[i];
            if (childdata.hospId == req.params.a) 
            {
                await firebase.database().ref("Users/Drivers/" + childdata.driverkey).once('value', async function (snapshot) {
                    var dinfo = snapshot.val();
                    var type= dinfo.service;
                    var bed=type.substring(type.length-1,type.length)
                    type=type.substring(0,type.length-2)
                    console.log(bed);
                    childdata.ambulanceType = type;
                    childdata.beds=bed;
                    childdata.ambulanceLicense = dinfo.license;
                });
                
                await firebase.database().ref("Users/Customers/" + childdata.CustomerId).once('value', function (snapshot) {
                    var custinfo = snapshot.val();
                    childdata.patient = custinfo;
                    DriverPatient.push(childdata);
                });
            }
        }
        console.log(DriverPatient);
        return DriverPatient;
    }

    var DriverPatient = await getDetails();

    res.render(__dirname + '/views/details.ejs', {
        drivers: DriverPatient
    });

})

app.get('/Profile-:a',async (req,res)=>{

    var hospId=req.params.a;
    var hospdata=[];
    await firebase.database().ref("Users/Hospitals").once('value', function (snapshot) {
        snapshot.forEach(function (childsnapshot) {
            var childdata = childsnapshot.val();
            if(childdata.id==hospId)
            hospdata.push(childdata);
        });
    });

    res.render(__dirname+'/views/Profile.ejs',{
        hospital:hospdata[0]
    })
})
app.listen(process.env.PORT || 3000)
