package com.pcc.PatientCareCenter.Database;

import java.util.*;

public class SriLankaDistricts {
    public static Map<String, List<String>> getDistrictsWithSubDistricts() {
        Map<String, List<String>> districts = new LinkedHashMap<>();

        // Add districts and their sub-districts
        districts.put("Ampara", Arrays.asList(
                "Akkarepattu", "Ampara", "Damana", "Dehiattakandiya", "Kalmunai", "Karaitivu", "Lahugala", "Mahaoya", "Navithanveli", "Nintavur", "Padiyathalawa", "Sainthamaruthu", "Sammanthurai", "Thirukkovil", "Uhana"
        ));

        districts.put("Anuradhapura", Arrays.asList(
                "Anuradhapura", "Galenbindunuwewa", "Galnewa", "Horowpothana", "Ipalogama", "Kahatagasdigiliya", "Kebithigollewa", "Kekirawa", "Medawachchiya", "Mihintale", "Nochchiyagama", "Nuwaragam Palatha East", "Nuwaragam Palatha Central", "Padaviya", "Palagala", "Palugaswewa", "Rajanganaya", "Rambewa", "Thalawa", "Thambuttegama", "Thirappane"
        ));

        districts.put("Badulla", Arrays.asList(
                "Badulla", "Bandarawela", "Ella", "Haldummulla", "Hali Ela", "Haputale", "Kandaketiya", "Lunugala", "Mahiyanganaya", "Meegahakivula", "Passara", "Rideemaliyadda", "Soranathota", "Uva Paranagama", "Welimada"
        ));

        districts.put("Batticaloa", Arrays.asList(
                "Batticaloa", "Eravur Pattu", "Eravur Town", "Kattankudy", "Koralai Pattu", "Koralai Pattu North", "Manmunai North", "Manmunai Pattu", "Manmunai South & Eruvil Pattu", "Manmunai West", "Porativu Pattu", "Koralai Pattu West"
        ));

        districts.put("Colombo", Arrays.asList(
                "Colombo", "Dehiwala", "Homagama", "Kaduwela", "Kesbewa", "Kolonnawa", "Kotte", "Maharagama", "Moratuwa", "Padukka", "Ratmalana", "Seethawaka", "Thimbirigasyaya"
        ));

        districts.put("Galle", Arrays.asList(
                "Ambalangoda", "Baddegama", "Balapitiya", "Bentota", "Bope-Poddala", "Elpitiya", "Galle", "Gonapinuwala", "Habaraduwa", "Hikkaduwa", "Imaduwa", "Karandeniya", "Nagoda", "Neluwa", "Thawalama", "Welivitiya-Divithura", "Yakkalamulla"
        ));

        districts.put("Gampaha", Arrays.asList(
                "Attanagalla", "Biyagama", "Divulapitiya", "Dompe", "Gampaha", "Ja-Ela", "Katana", "Kelaniya", "Mahara", "Minuwangoda", "Mirigama", "Negombo", "Wattala"
        ));

        districts.put("Hambantota", Arrays.asList(
                "Ambalantota", "Angunakolapelessa", "Beliatta", "Hambantota", "Katuwana", "Lunugamvehera", "Okewela", "Sooriyawewa", "Tangalle", "Thissamaharama", "Walasmulla", "Weeraketiya"
        ));

        districts.put("Jaffna", Arrays.asList(
                "Chavakachcheri", "Delft", "Island North", "Island South", "Jaffna", "Karainagar", "Nallur", "Thenmaradchi", "Vadamaradchi East", "Vadamaradchi North", "Vadamaradchi South-West", "Valikamam East", "Valikamam North", "Valikamam South", "Valikamam South-West", "Valikamam West"
        ));

        districts.put("Kalutara", Arrays.asList(
                "Agalawatta", "Bandaragama", "Beruwala", "Bulathsinhala", "Dodangoda", "Horana", "Ingiriya", "Kalutara", "Madurawala", "Matugama", "Millaniya", "Palindanuwara", "Panadura", "Walallavita"
        ));

        districts.put("Kandy", Arrays.asList(
                "Akurana", "Delthota", "Doluwa", "Ganga Ihala Korale", "Harispattuwa", "Hatharaliyadda", "Kandy", "Kundasale", "Medadumbara", "Minipe", "Panvila", "Pasbage Korale", "Pathadumbara", "Pathahewaheta", "Poojapitiya", "Thumpane", "Udadumbara", "Udapalatha", "Udunuwara", "Yatinuwara"
        ));

        districts.put("Kegalle", Arrays.asList(
                "Aranayaka", "Bulathkohupitiya", "Dehiovita", "Deraniyagala", "Galigamuwa", "Kegalle", "Mawanella", "Rambukkana", "Ruwanwella", "Warakapola", "Yatiyanthota"
        ));

        districts.put("Kilinochchi", Arrays.asList(
                "Kandavalai", "Karachchi", "Pachchilaipalli", "Poonakary"
        ));

        districts.put("Kurunegala", Arrays.asList(
                "Alawwa", "Bingiriya", "Galgamuwa", "Giribawa", "Ibbagamuwa", "Kuliyapitiya", "Kurunegala", "Maho", "Mallawapitiya", "Narammala", "Nikaweratiya", "Panduwasnuwara", "Polgahawela", "Polpithigama", "Rasnayakapura", "Rideegama", "Udubaddawa", "Wariyapola"
        ));

        districts.put("Mannar", Arrays.asList(
                "Madhu", "Mannar", "Manthai West", "Musalai", "Nanaddan"
        ));

        districts.put("Matale", Arrays.asList(
                "Dambulla", "Galewela", "Laggala-Pallegama", "Matale", "Naula", "Pallepola", "Rattota", "Ukuwela", "Wilgamuwa", "Yatawatta"
        ));

        districts.put("Matara", Arrays.asList(
                "Akuressa", "Athuraliya", "Devinuwara", "Dikwella", "Hakmana", "Kamburupitiya", "Kirinda-Puhulwella", "Kotapola", "Malimbada", "Matara", "Mulatiyana", "Pasgoda", "Pitabeddara", "Thihagoda", "Weligama"
        ));

        districts.put("Monaragala", Arrays.asList(
                "Badalkumbura", "Bibile", "Buttala", "Katharagama", "Madulla", "Medagama", "Monaragala", "Sevanagala", "Siyambalanduwa", "Thanamalvila", "Wellawaya"
        ));

        districts.put("Mullaitivu", Arrays.asList(
                "Manthai East", "Maritimepattu", "Oddusuddan", "Puthukudiyiruppu", "Thunukkai"
        ));

        districts.put("Nuwara Eliya", Arrays.asList(
                "Ambagamuwa", "Hanguranketha", "Kothmale", "Nuwara Eliya", "Walapane"
        ));

        districts.put("Polonnaruwa", Arrays.asList(
                "Dimbulagala", "Elahera", "Hingurakgoda", "Lankapura", "Medirigiriya", "Polonnaruwa", "Thamankaduwa", "Welikanda"
        ));

        districts.put("Puttalam", Arrays.asList(
                "Anamaduwa", "Arachchikattuwa", "Chilaw", "Dankotuwa", "Kalpitiya", "Karuwalagaswewa", "Madampe", "Mahakumbukkadawala", "Mahawewa", "Mundalama", "Nattandiya", "Nawagattegama", "Pallama", "Puttalam", "Vanathavilluwa", "Wennappuwa"
        ));

        districts.put("Ratnapura", Arrays.asList(
                "Ayagama", "Balangoda", "Eheliyagoda", "Elapatha", "Embilipitiya", "Godakawela", "Imbulpe", "Kahawatta", "Kalawana", "Kiriella", "Kolonna", "Kuruwita", "Nivithigala", "Opanayaka", "Pelmadulla", "Ratnapura", "Weligepola"
        ));

        districts.put("Trincomalee", Arrays.asList(
                "Gomarankadawala", "Kantalai", "Kinniya", "Kuchchaveli", "Morawewa", "Muttur", "Padavi Sri Pura", "Seruvila", "Thambalagamuwa", "Trincomalee"
        ));

        districts.put("Vavuniya", Arrays.asList(
                "Vavuniya", "Vavuniya North", "Vavuniya South", "Vengalacheddikulam"
        ));

        return districts;
    }

    public static void main(String[] args) {
        Map<String, List<String>> districts = getDistrictsWithSubDistricts();
        districts.forEach((district, subDistricts) -> {
            System.out.println("District: " + district);
            System.out.println("Sub-Districts: " + String.join(", ", subDistricts));
            System.out.println();
        });
    }
}