import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.collections.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;
import java.util.*;

public class kontroller {

    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis hariAxis;
    @FXML private NumberAxis nilaiAxis;

    @FXML private TableView<RiwayatHari> tabelRiwayat;
    @FXML private TableColumn<RiwayatHari, String> kolomTanggal;
    @FXML private TableColumn<RiwayatHari, Double> kolomRataRata;
    @FXML private TableColumn<RiwayatHari, String> kolomKeterangan;

    @FXML private TextField txtStres, txtRelaks, txtOverwhelmed, txtProduktif;

    private XYChart.Series<String, Number> dataMingguan = new XYChart.Series<>();
    private ObservableList<RiwayatHari> riwayatData = FXCollections.observableArrayList();

    private Map<String, Double> dataHarian = new LinkedHashMap<>();

    private final String[] hariMinggu = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"};
    private int indexHari = 0;

    @FXML
    public void initialize() {
        hariAxis.setCategories(FXCollections.observableArrayList(Arrays.asList(hariMinggu)));
        barChart.getData().add(dataMingguan);

        kolomTanggal.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        kolomRataRata.setCellValueFactory(new PropertyValueFactory<>("rataRata"));
        kolomKeterangan.setCellValueFactory(new PropertyValueFactory<>("keterangan"));

        tabelRiwayat.setItems(riwayatData);
    }

    @FXML
    private void handleSubmit() {
        try {
            int stres = Integer.parseInt(txtStres.getText());
            int relaks = Integer.parseInt(txtRelaks.getText());
            int over = Integer.parseInt(txtOverwhelmed.getText());
            int produktif = Integer.parseInt(txtProduktif.getText());

            double rata2 = (stres + relaks + over + produktif) / 4.0;

            if (indexHari >= hariMinggu.length) {
                dataMingguan.getData().clear();
                indexHari = 0;
            }
            dataMingguan.getData().add(new XYChart.Data<>(hariMinggu[indexHari], rata2));
            indexHari++;

            LocalDate hariIni = LocalDate.now();
            String keterangan = getKeterangan(rata2);

            riwayatData.add(new RiwayatHari(hariIni.toString(), rata2, keterangan));

            txtStres.clear();
            txtRelaks.clear();
            txtOverwhelmed.clear();
            txtProduktif.clear();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Masukkan angka dari 1 sampai 10 untuk semua isian.");
            alert.show();
        }
    }

    private String getKeterangan(double rata2) {
        if (rata2 >= 8) return "Hari Baik";
        else if (rata2 >= 5) return "Lumayan";
        else return "Hari Buruk";
    }

    public static class RiwayatHari {
        private final String tanggal;
        private final Double rataRata;
        private final String keterangan;

        public RiwayatHari(String tanggal, Double rataRata, String keterangan) {
            this.tanggal = tanggal;
            this.rataRata = rataRata;
            this.keterangan = keterangan;
        }

        public String getTanggal() { return tanggal; }
        public Double getRataRata() { return rataRata; }
        public String getKeterangan() { return keterangan; }
    }
}
