import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class kontroller {

    @FXML
    private BarChart<String, Number> diagramBatang;

    @FXML
    private TableView<DataHari> tabelNilai;

    @FXML
    private TableColumn<DataHari, String> kolomTanggal;

    @FXML
    private TableColumn<DataHari, Number> kolomNilai;

    @FXML
    private TableColumn<DataHari, String> kolomMasalah;

    private int hariKe = 1;

    @FXML
    private void initialize() {
        kolomTanggal.setCellValueFactory(data -> data.getValue().tanggalProperty());
        kolomNilai.setCellValueFactory(data -> data.getValue().nilaiProperty());
        kolomMasalah.setCellValueFactory(data -> data.getValue().masalahProperty());

        diagramBatang.getData().clear();
    }

    @FXML
    private void tambahHariBaru(ActionEvent event) {
        LocalDate tanggal = LocalDate.now().plusDays(hariKe - 1);
        int nilai = (hariKe % 10) + 1;
        String masalah = "Masalah hari ke-" + hariKe;

        DataHari dataHari = new DataHari(tanggal.format(DateTimeFormatter.ISO_DATE), nilai, masalah);

        tabelNilai.getItems().add(dataHari);

        XYChart.Series<String, Number> series;
        if (diagramBatang.getData().isEmpty()) {
            series = new XYChart.Series<>();
            series.setName("Nilai Minggu Ini");
            diagramBatang.getData().add(series);
        } else {
            series = diagramBatang.getData().get(0);
        }
        series.getData().add(new XYChart.Data<>(dataHari.getTanggal(), dataHari.getNilai()));

        hariKe++;

        if ((hariKe - 1) % 7 == 0) {
            diagramBatang.getData().clear();
        }
    }


    

    public static class DataHari {
        private final SimpleStringProperty tanggal;
        private final SimpleIntegerProperty nilai;
        private final SimpleStringProperty masalah;

        public DataHari(String tanggal, int nilai, String masalah) {
            this.tanggal = new SimpleStringProperty(tanggal);
            this.nilai = new SimpleIntegerProperty(nilai);
            this.masalah = new SimpleStringProperty(masalah);
        }

        public String getTanggal() {
            return tanggal.get();
        }

        public SimpleStringProperty tanggalProperty() {
            return tanggal;
        }

        public int getNilai() {
            return nilai.get();
        }

        public SimpleIntegerProperty nilaiProperty() {
            return nilai;
        }

        public String getMasalah() {
            return masalah.get();
        }

        public SimpleStringProperty masalahProperty() {
            return masalah;
        }
    }
}
