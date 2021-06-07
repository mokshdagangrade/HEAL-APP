package com.example.heal.customerui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.heal.Adapter.HospitalListAdapter;
import com.example.heal.R;

import java.util.ArrayList;
import java.util.Collections;

public class HospitalsFragment extends Fragment {
    Button btFind,buttonHospital;
    ArrayList<String> arrayList;
    ListView mListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hospitals, null, false);
        buttonHospital=(Button) view.findViewById(R.id.buttonHospital);
        Spinner spinner = view.findViewById(R.id.spinner);
        mListView = view.findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        arrayList.add("Delhi");
        arrayList.add("Mumbai");
        arrayList.add("Chennai");
        arrayList.add("Bengaluru");
        arrayList.add("Pune");
        arrayList.add("Kolkata");
        arrayList.add("Jaipur");
        arrayList.add("Hyderabad");
        arrayList.add("Bhopal");
        arrayList.add("Ahemdabad");
        Collections.sort(arrayList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tutorialsName = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + tutorialsName,          Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {
            }
        });

        btFind = view.findViewById(R.id.bt_find);
        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = spinner.getSelectedItem().toString();
                switch (name){
                    case "Delhi": dispDelhiHospitals();break;
                    case "Mumbai": dispMumbaiHospitals();break;
                    case "Chennai": dispChennaiHospitals();break;
                    case "Bengaluru": dispbengHospitals();break;
                    case "Pune" : dispPuneHospitals();break;
                    case "Kolkata": dispKolkataHospitals();break;
                    case "Jaipur" : dispJaipurHospitals();break;
                    case "Hyderabad": dispHyderabadHospitals();break;
                    case "Bhopal" : dispBhopalHospitals();break;
                    case "Ahemdabad" : dispAhemHospitals();break;
                }

            }
        });
        buttonHospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),HospitalsMapsActivity.class));
            }
        });

        return view;
    }

    private void dispAhemHospitals() {
        Hospital RSVH = new Hospital("Apollo Hospital International Limited", "Plot No, 1A, Gandhinagar - Ahmedabad Rd, GIDC Bhat, Bhat, Ahmedabad, Gujarat 382428", "079 6670 1800");

        Hospital FMC = new Hospital("Avron Hospitals PVT. LTD.",
                "Avron hospital , 4, Shantiniketan Park, Sardar Patel Colony, Ahmedabad, Gujarat 380013",
                "090818 30100");

        Hospital KMC = new Hospital("Narayana Multispeciality Hospital, Ahmedabad",
                "Police Station, Nr. Chakudiya Mahadev, Cross Rd, opp. Rakhial, Rakhial, Ahmedabad, Gujarat 380023",
                "080675 06878");

        Hospital AH = new Hospital("Apollo Hospitals City Centre",
                "No.1, Tulsi Baugh Society, Opp. Doctor House, Ambawadi, near Parimal Garden, Ahmedabad, Gujarat 380006",
                "079 6630 5800");

        Hospital CHC = new Hospital("Zydus Hospitals",
                "Zydus Hospitals Road, Nr. Sola Bridge, Sarkhej - Gandhinagar Hwy, Ahmedabad, Gujarat 380054",
                "079 6619 0201");
        Hospital KMCH = new Hospital("HCG Hospitals",
                "Mithakhali Six Rd, kalyan Society, Maharashtra Society, Ellisbridge, Ahmedabad, Gujarat 380006",
                "063588 88815");


        ArrayList<Hospital> HospitalList= new ArrayList<>();
        HospitalList.add(RSVH);
        HospitalList.add(FMC);
        HospitalList.add(KMC);
        HospitalList.add(AH);
        HospitalList.add(CHC);
        HospitalList.add(KMCH);


        HospitalListAdapter adapter = new HospitalListAdapter(getActivity(),R.layout.adapter_view,HospitalList);
        mListView.setAdapter(adapter);

    }

    private void dispBhopalHospitals() {
        Hospital RSVH = new Hospital("Bansal Hospital Bhopal",
                "Chuna Bhatti Rd, Manisha Market, Sector C, Shahpura, Bhopal, Madhya Pradesh 462016",
                "0755 408 6000");

        Hospital FMC = new Hospital("New Bhopal Hospital Research Foundation",
                "170, Zone-II, Near Allahabad Bank, Near Sargam Cinema, Zone-II, Maharana Pratap Nagar, Bhopal, Madhya Pradesh 462023",
                "0755 255 5333");

        Hospital KMC = new Hospital("Bhopal City Hospital",
                "Hoshangabad Road,Main Road Madan Market, opposite Government Hospital, Misrod, Bhopal, Madhya Pradesh 462026",
                "-");

        Hospital AH = new Hospital("Chirayu Health & Medicare Pvt. Ltd.",
                "Nakkar Khana, Peer Gate, Bhopal, Madhya Pradesh 462001",
                "0755 273 7401");

        Hospital CHC = new Hospital("LBS Hospital",
                "73, Motia Talab Rd, opp. Motia Lake, Motia Talab, Kali Basti, Kohefiza, Bhopal, Madhya Pradesh 462001",
                "0755 273 3433");
        Hospital KMCH = new Hospital("Arnav Hospital, Bhopal",
                "F- Sector, Raisen Rd, Near NRI College, Patel Nagar, Bhopal, Madhya Pradesh 462022",
                "0755 252 9944");


        ArrayList<Hospital> HospitalList= new ArrayList<>();
        HospitalList.add(RSVH);
        HospitalList.add(FMC);
        HospitalList.add(KMC);
        HospitalList.add(AH);
        HospitalList.add(CHC);
        HospitalList.add(KMCH);


        HospitalListAdapter adapter = new HospitalListAdapter(getActivity(),R.layout.adapter_view,HospitalList);
        mListView.setAdapter(adapter);

    }

    private void dispHyderabadHospitals() {
        Hospital RSVH = new Hospital("Medicover Hospitals HITEC City ",
                "behind Cyber Towers, In the Lane of IBIS Hotels, HUDA Techno Enclave, HITEC City, Hyderabad, Telangana 500081",
                "040 6833 4455");

        Hospital FMC = new Hospital("Hyderabad MultiSpeciality Hospital",
                "16-2-674/11, Judges Colony, New Malakpet, Hyderabad, Telangana 500036",
                "083097 71005");

        Hospital KMC = new Hospital("Yashoda Hospitals",
                "16-10-29, Nalgonda X Roads, Near New Market Metro station, Malakpet, Hyderabad, Telangana 500036",
                "040 4567 4567");

        Hospital AH = new Hospital("Century Super Speciality Hospital",
                "Rd Number 12, Bhola Nagar, Banjara Hills, Hyderabad, Telangana 500034",
                "040 6783 3333");

        Hospital CHC = new Hospital("Virinchi Hospitals",
                "Virinchi Circle, Rd Number 1, Shyam Rao Nagar, Banjara Hills, Hyderabad, Telangana 500034",
                "040 4699 9999");
        Hospital KMCH = new Hospital("Omega Hospitals",
                "L/276A, Rd Number 12, MLA Colony, Banjara Hills, Hyderabad, Telangana 500034",
                "040 2355 1034");


        ArrayList<Hospital> HospitalList= new ArrayList<>();
        HospitalList.add(RSVH);
        HospitalList.add(FMC);
        HospitalList.add(KMC);
        HospitalList.add(AH);
        HospitalList.add(CHC);
        HospitalList.add(KMCH);


        HospitalListAdapter adapter = new HospitalListAdapter(getActivity(),R.layout.adapter_view,HospitalList);
        mListView.setAdapter(adapter);


    }

    private void dispJaipurHospitals() {
        Hospital RSVH = new Hospital("Shalby Multi-Specialty Hospital, Jaipur",
                "Under Pass, Delhi - Ajmer Expressway 200 Feet Bypass Road, near Gandhi Path, Chitrakoot Sector 3, Vaishali Nagar, Jaipur, Rajasthan 302021",
                "0141 712 3889");

        Hospital FMC = new Hospital("Fortis Escorts Hospital, Jaipur",
                "Jawahar Lal Nehru Marg, Sector 5, Malviya Nagar, Jaipur, Rajasthan 302017",
                "0141 254 7000");

        Hospital KMC = new Hospital("Jaipur Hospital, Mahaveer Nagar",
                "S16A, S17, Mahaveer Nagar, Tonk Toad, Flyover, near Gopalpura, Jaipur, Rajasthan 302018",
                "0141 255 1500");

        Hospital AH = new Hospital("Apollo Spectra Hospitals",
                "J-2/37, Mahavir Marg, opp. Jai Club, Panch Batti, C Scheme, Ashok Nagar, Jaipur, Rajasthan 302001",
                "084484 40991");

        Hospital CHC = new Hospital("Sanjeevani Hospital And Medical Research Institute Jaipur",
                "New Sanganer Road Opp. Metro Piller No. 91, Roop Vihar, Goverdhan Colony, Vivek Vihar, Sodala, Jaipur, Rajasthan 302019",
                "0141 406 7000");
        Hospital KMCH = new Hospital("Bhandari Hospital & Research Centre",
                "138-A,Vasundhra Colony, Gopalpura Bypass, Tonk Rd, Jaipur, Rajasthan 302018",
                "096600 06228");


        ArrayList<Hospital> HospitalList= new ArrayList<>();
        HospitalList.add(RSVH);
        HospitalList.add(FMC);
        HospitalList.add(KMC);
        HospitalList.add(AH);
        HospitalList.add(CHC);
        HospitalList.add(KMCH);


        HospitalListAdapter adapter = new HospitalListAdapter(getActivity(),R.layout.adapter_view,HospitalList);
        mListView.setAdapter(adapter);


    }

    private void dispKolkataHospitals() {
        Hospital RSVH = new Hospital("RSV Hospital",
                "40, Deshapran Sasmal Rd, Tollygunge Phari, Tollygunge, Kolkata, West Bengal 700033",
                "033 3001 3000");

        Hospital FMC = new Hospital("Fortis Medical Centre",
                "2, 7, Sarat Bose Rd, opp. Minto Park, Sreepally, Bhowanipore, Kolkata, West Bengal 700020",
                "033 6620 2000");

        Hospital KMC = new Hospital("Kothari Medical Centre",
                "8/3, Alipore Rd, Alipore, Kolkata, West Bengal 700027",
                "033 4012 7000");

        Hospital AH = new Hospital("AMRI HOSPITAL, SALT LAKE",
                "16 17, JC Block Lane, Central Park Road Broadway Road, stadium gate number 3, opposite salt lake, Sector III, Bidhannagar, Kolkata, West Bengal 700098",
                "033 6606 3800");

        Hospital CHC = new Hospital("Calcutta Heart Clinic & Hospital",
                "3, 1st Cross Rd, HC Block, Sector III, Bidhannagar, Kolkata, West Bengal 700106",
                "033 2358 5735");
        Hospital KMCH = new Hospital("Kolkata Medical Complex Hospital",
                "Plot No:21, Narkeldanga Main Rd, Narkeldanga, Kolkata, West Bengal 700011",
                "033 2363 3213");


        ArrayList<Hospital> HospitalList= new ArrayList<>();
        HospitalList.add(RSVH);
        HospitalList.add(FMC);
        HospitalList.add(KMC);
        HospitalList.add(AH);
        HospitalList.add(CHC);
        HospitalList.add(KMCH);


        HospitalListAdapter adapter = new HospitalListAdapter(getActivity(),R.layout.adapter_view,HospitalList);
        mListView.setAdapter(adapter);

    }

    private void dispPuneHospitals() {
        Hospital DMHH = new Hospital("Deccan Multispeciality Hardikar Hospital",
                "1160/61, University Rd, Revenue Colony, Shivajinagar, Pune, Maharashtra 411005",
                "020 4109 5000");
        Hospital PH = new Hospital("Poona Hospital And Research Centre",
                "Shivam, 27, Shivram Matre Rd, Nr. Alka Talkies, Ganjwe Wadi, Sadashiv Peth, Pune, Maharashtra 411030",
                "020 6609 6000");
        Hospital CAH= new Hospital("Columbia Asia Hospital Pune",
                "22, 2A, Mundhwa - Kharadi Rd, Near Nyati Empire, Santipur, Thite Nagar, Kharadi, Pune, Maharashtra 411014",
                "020 6165 6666");
        Hospital JH= new Hospital("Jehangir Hospital",
                "32, Sasoon Road Opposite Railway Station, Central Excise Colony, Sangamvadi, Pune, Maharashtra 411001",
                "020 6681 9999");
        Hospital NPH= new Hospital("New Pune Hospital",
                "Sr no 50/7, Gulmohar Apartment, Kondhwa Rd, behind Satyanand Hospital, Shivneri Nagar, Kondhwa, Pune, Maharashtra 411048",
                "020 6050 0251");
        Hospital RH= new Hospital("Ranka Hospital",
                "157 / 5, SS Dhage Rd, Mukund Nagar, near Swargate, Pune, Maharashtra 411037",
                "020 2426 1600");

        ArrayList<Hospital> HospitalList= new ArrayList<>();
        HospitalList.add(DMHH);
        HospitalList.add(PH);
        HospitalList.add(CAH);
        HospitalList.add(JH);
        HospitalList.add(NPH);
        HospitalList.add(RH);


        HospitalListAdapter adapter = new HospitalListAdapter(getActivity(),R.layout.adapter_view,HospitalList);
        mListView.setAdapter(adapter);

    }

    private void dispbengHospitals() {
        Hospital TBH = new Hospital("The Bangalore Hospital",
                "202, Rashtriya Vidyalaya Rd, 2nd Block, Basavanagudi, Bengaluru, Karnataka 560004",
                "080 4278 1643");
        Hospital FH= new Hospital("Fortis Hospital, Bannerghatta Main, Bangalore",
                "154, 9, Bannerghatta Main Rd, Opposite IIM, Sahyadri Layout, Panduranga Nagar, Bengaluru, Karnataka 560076",
                "096633 67253");
        Hospital CARH= new Hospital("Columbia Asia Referral Hospital – Yeshwanthpur",
                "26/4, Brigade Gateway, beside Metro, Malleshwaram, Bengaluru, Karnataka 560055",
                "080 6165 6262");
        Hospital NBH= new Hospital("North Bangalore Hospital",
                "No.125/1, 125/2, SY No.104, 4th, G Street, Chelekere Village, Outer Ring Road, Kalyananagara, Next To Kalyananagara Bus Depo, Bengaluru, Karnataka 560043",
                "091643 99999");
        Hospital FHR= new Hospital("Fortis Hospital Rajajinagar Bangalore - Karnataka",
                "111, West of Chord Road Opp Rajajinagar, 1st Block, Junction, Bengaluru, Karnataka 560086",
                "076187 72262");
        Hospital BBH= new Hospital("Bangalore Baptist Hospital",
                "Bellary Rd, Vinayakanagar, Hebbal, Bengaluru, Karnataka 560024",
                "080 2202 4700");

        ArrayList<Hospital> HospitalList= new ArrayList<>();
        HospitalList.add(TBH);
        HospitalList.add(FH);
        HospitalList.add(CARH);
        HospitalList.add(NBH);
        HospitalList.add(FHR);
        HospitalList.add(BBH);


        HospitalListAdapter adapter = new HospitalListAdapter(getActivity(),R.layout.adapter_view,HospitalList);
        mListView.setAdapter(adapter);

    }

    private void dispChennaiHospitals() {
        Hospital AHC = new Hospital("Apollo Hospital Chennai",
                "21 Greams Lane, Off, Greams Road, Thousand Lights, Chennai, Tamil Nadu 600006",
                "044 2829 3333");
        Hospital FMH= new Hospital("Fortis Malar Hospital, Adyar, Chennai",
                "No. 52, 1st Main Rd, Gandhi Nagar, Adyar, Chennai, Tamil Nadu 600020",
                "099625 99933");
        Hospital DMH= new Hospital("Dr. Mehta's Hospitals",
                "No.2, Mc Nichols Rd, Chetpet, Chennai, Tamil Nadu 600031",
                "044 4227 1001");
        Hospital CNH= new Hospital("CHENNAI NATIONAL HOSPITAL",
                "12, Jaffar Sarang St, Parrys, George Town, Chennai, Tamil Nadu 600001",
                "089390 33333");
        Hospital SH= new Hospital("Sakthi Hospital & Research Centre, Chennai",
                "175, Big St, Police Quarters, Triplicane, Chennai, Tamil Nadu 600005",
                "044 2844 2626");
        Hospital MIH= new Hospital("MedIndia Hospitals",
                "Police Station, 83, Valluvar Kottam High Rd, near to nungambakkam, Chennai, Tamil Nadu 600034",
                "044 2831 1415");

        Hospital KMH= new Hospital("DR.KAMAKSHI MEMORIAL HOSPITALS",
                "#1, Radial Road, Dandeeswarar Nagar, Rose Avenue, Pallikaranai, Chennai, Tamil Nadu 600100",
                "044 6630 0300");

        ArrayList<Hospital> HospitalList= new ArrayList<>();
        HospitalList.add(AHC);
        HospitalList.add(FMH);
        HospitalList.add(DMH);
        HospitalList.add(CNH);
        HospitalList.add(SH);
        HospitalList.add(MIH);
        HospitalList.add(KMH);


        HospitalListAdapter adapter = new HospitalListAdapter(getActivity(),R.layout.adapter_view,HospitalList);
        mListView.setAdapter(adapter);

    }

    private void dispMumbaiHospitals() {
        Hospital GH = new Hospital("Global Hospitals",
                "35, Dr. E, Dr Ernest Borges Rd, opp. Shirodkar High School, Parel, Mumbai, Maharashtra 400012",
                "022 6767 0101");
        Hospital WH= new Hospital("Wockhardt Hospitals, Mumbai Central",
                "Police Station, 1877 Doctor Anandrao Nair Marg Near Agripada, Mumbai Central, Mumbai, Maharashtra 400011",
                "082911 01001");
        Hospital JHRC= new Hospital("Jaslok Hospital and Research Centre",
                "15, Pedder Rd, IT Colony, Tardeo, Mumbai, Maharashtra 400026",
                "022 6657 3014");
        Hospital KDAH= new Hospital("Kokilaben Dhirubhai Ambani Hospital and Medical Research Institute",
                "Rao Saheb, Achutrao Patwardhan Marg, Four Bungalows, Andheri West, Mumbai, Maharashtra 400053",
                "022 4269 6969");
        Hospital Sh= new Hospital("Saifee Hospital",
                "15/17, Maharshi Karve Rd, Charni Road East, Opera House, Girgaon, Mumbai, Maharashtra 400004",
                "022 6757 0111");
        ArrayList<Hospital> HospitalList= new ArrayList<>();
        HospitalList.add(GH);
        HospitalList.add(WH);
        HospitalList.add(JHRC);
        HospitalList.add(KDAH);
        HospitalList.add(Sh);


        HospitalListAdapter adapter = new HospitalListAdapter(getActivity(),R.layout.adapter_view,HospitalList);
        mListView.setAdapter(adapter);
    }

    private void dispDelhiHospitals() {
        Hospital DNSH = new Hospital("Dharamshila Narayana Superspeciality Hospital",
                "Dharamshila Marg, Vasundhara Enclave, Delhi - 110096, Near New Ashok Nagar Metro Station",
                "7290084231");
        Hospital MHD= new Hospital("Manipal Hospital Delhi",
                "Palam Vihar, Sector 6 Dwarka, Dwarka, New Delhi, Delhi 110075",
                "011 4967 4967");
        Hospital BGH= new Hospital("Bansal Global Hospital",
                "C-10, near Jahangirpuri Metro Station, Ramgarh, Ramgarh Village, Jahangirpuri, Delhi, 110033",
                "098111 62832");
        Hospital DMSH= new Hospital("DELHI MULTI SPECIALITY HOSPITAL",
                "I-21, Vijay Vihar Phase II, Vijay Vihar, Sector 4, Rohini, Delhi, 110085",
                "099997 89099");
        Hospital ASH= new Hospital("Apollo Spectra Hospitals",
                "A-19/A, Block A, Sector 19, near Kailash Colony Metro Station, Kailash Colony, New Delhi, Delhi 110048",
                "011 4046 5555");

        ArrayList<Hospital> HospitalList= new ArrayList<>();
        HospitalList.add(DNSH);
        HospitalList.add(MHD);
        HospitalList.add(BGH);
        HospitalList.add(DMSH);
        HospitalList.add(ASH);


        HospitalListAdapter adapter = new HospitalListAdapter(getActivity(),R.layout.adapter_view,HospitalList);
        mListView.setAdapter(adapter);
    }
    }