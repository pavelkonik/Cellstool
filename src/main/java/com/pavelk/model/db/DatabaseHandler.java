package com.pavelk.model.db;


import com.pavelk.main.Cell;
import com.pavelk.main.Const;
import com.pavelk.model.Model;
import com.pavelk.uicontrollers.ControllerCellsToKML;
import com.pavelk.main.Pd;
import com.pavelk.uicontrollers.ControllerDBaccessForm;
import com.pavelk.uicontrollers.ControllerPDCellsToKML;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends Model {

    private String dbHost = "172.17.112.50";
    private String dbUser = "";
    private String dbPass = "";

    private Connection dbConnection;

    private boolean isConnected = true;

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public void getUserPassHost() {
        ControllerDBaccessForm controllerDBaccessForm = new ControllerDBaccessForm();
        controllerDBaccessForm.DBaccessWindow();

        dbUser = DBAccess.dbUser;
        DBAccess.dbUser = null;
        dbPass = DBAccess.dbPass;
        DBAccess.dbPass = null;
        dbHost = DBAccess.dbHost;
        DBAccess.dbHost = null;
    }

    public void setLogInfo(String s) {
        if (ControllerCellsToKML.cellsOrPDWindows != null)
            if (ControllerCellsToKML.cellsOrPDWindows) {
                ControllerCellsToKML.logInfo.setLogData(s);
             //   ControllerCellsToKML.cellsOrPDWindows = null;
            }
        if (ControllerPDCellsToKML.cellsOrPDWindows != null)
            if (!ControllerPDCellsToKML.cellsOrPDWindows) {
                ControllerPDCellsToKML.logInfo.setLogData(s);
             //   ControllerPDCellsToKML.cellsOrPDWindows = null;
            }
    }

    public Connection getDbConnection(boolean mySQLConnection) throws SQLException {
        getUserPassHost();
        if (dbUser.equals("") || dbHost.equals("")) return null;
        try {
            if (mySQLConnection) {
                String connectionString = "jdbc:mysql://" + dbHost + ":" + Const.DB_PORT_MY_SQL + "/" + Const.DB_NAME_MY_SQL + "?serverTimezone=UTC";
                Class.forName("com.mysql.cj.jdbc.Driver");
                dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
                setLogInfo("Connected to MySQL" + '\n');
            } else {
                String connectionString = "jdbc:vertica://" + dbHost + ":" + Const.DB_PORT_VERTICA + "/" + Const.DB_NAME_VERTICA;
                Class.forName("com.vertica.jdbc.Driver");
                dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
                ControllerPDCellsToKML.logInfo.setLogData("Connected to Vertica" + '\n');
            }
            return dbConnection;
        } catch (Exception e) {
            isConnected = false;
            if (dbConnection != null) dbConnection.close();
            setLogInfo("Connection problem to " + dbHost + " " + e + '\n');
            return null;
        }
    }

    public List<Cell> getTnetSectorSQL() throws SQLException {
        String select =
                "SELECT " + Const.CELLNAME + "," + Const.CELLID + "," + Const.AZIMUTH + "," + Const.CPICHPOWRE + "," + Const.HEIGHT + "," + Const.LATITUDE
                        + "," + Const.LONGITUDE + "," + Const.PSC + " FROM " + Const.CELLS_TABLE
                        + "  WHERE network_id = (select max(id) from son.t_net_network) AND sector_type like 'UTRAN' LIMIT 50000";
        List<Cell> cells = null;
        try (PreparedStatement preparedStatement = getDbConnection(true).prepareStatement(select);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet != null) {
                cells = new ArrayList<>();
                try {
                    while (resultSet.next()) {
                        cells.add(new Cell(resultSet.getString(Const.CELLNAME), resultSet.getString(Const.CELLID), resultSet.getString(Const.PSC), resultSet.getString(Const.AZIMUTH),
                                resultSet.getString(Const.LATITUDE), resultSet.getString(Const.LONGITUDE), resultSet.getString(Const.CPICHPOWRE), resultSet.getString(Const.HEIGHT)));
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            setLogInfo(cells.size() + " cells were get from DB" + '\n');
            dbConnection.close();
        } catch (SQLException e) {
            setLogInfo("SQL request error " + e + '\n');
            e.printStackTrace();
        } finally {
            if (dbConnection != null) dbConnection.close();
        }
        return cells;
    }

    public List<Pd> getPDSQL(List<Cell> cells, LocalDate startTime, LocalDate endTime, boolean agregate) throws SQLException {//LocalDate start_time, LocalDate end_time
        StringBuilder sb = new StringBuilder("");
        for (Cell cell : cells) {
            sb.append(cell.getCellId()).append(",");
        }
        String selectdate = "";
        String grouporderdate = "";
        if (!agregate) {
            selectdate = ", trunc(to_timestamp(end_time),'DDD') as date ";
            grouporderdate = ", date";
        }
        if (sb.indexOf(",") > 0)
            sb.delete(sb.length() - 1, sb.length());
        String select = "SELECT CI" + selectdate + ", " +
                "sum(PROP_DELAY_RANGE_0) as PD0," +
                "sum(PROP_DELAY_RANGE_1) as PD1," +
                "sum(PROP_DELAY_RANGE_2) as PD2," +
                "sum(PROP_DELAY_RANGE_3) as PD3," +
                "sum(PROP_DELAY_RANGE_4) as PD4," +
                "sum(PROP_DELAY_RANGE_5) as PD5," +
                "sum(PROP_DELAY_RANGE_6) as PD6," +
                "sum(PROP_DELAY_RANGE_7) as PD7," +
                "sum(PROP_DELAY_RANGE_8) as PD8," +
                "sum(PROP_DELAY_RANGE_9) as PD9," +
                "sum(PROP_DELAY_RANGE_10) as PD10," +
                "sum(PROP_DELAY_RANGE_11) as PD11," +
                "sum(PROP_DELAY_RANGE_12) as PD12," +
                "sum(PROP_DELAY_RANGE_13) as PD13," +
                "sum(PROP_DELAY_RANGE_14) as PD14," +
                "sum(PROP_DELAY_RANGE_15) as PD15," +
                "sum(PROP_DELAY_RANGE_16) as PD16," +
                "sum(PROP_DELAY_RANGE_17) as PD17," +
                "sum(PROP_DELAY_RANGE_18) as PD18," +
                "sum(PROP_DELAY_RANGE_19) as PD19," +
                "sum(PROP_DELAY_RANGE_20) as PD20," +
                "sum(PROP_DELAY_RANGE_21) as PD21," +
                "sum(PROP_DELAY_RANGE_22) as PD22," +
                "sum(PROP_DELAY_RANGE_23) as PD23," +
                "sum(PROP_DELAY_RANGE_24) as PD24," +
                "sum(PROP_DELAY_RANGE_25) as PD25," +
                "sum(PROP_DELAY_RANGE_26) as PD26," +
                "sum(PROP_DELAY_RANGE_27) as PD27," +
                "sum(PROP_DELAY_RANGE_28) as PD28," +
                "sum(PROP_DELAY_RANGE_29) as PD29," +
                "sum(PROP_DELAY_RANGE_30) as PD30," +
                "sum(PROP_DELAY_RANGE_31) as PD31," +
                "sum(PROP_DELAY_RANGE_32) as PD32," +
                "sum(PROP_DELAY_RANGE_33) as PD33," +
                "sum(PROP_DELAY_RANGE_34) as PD34," +
                "sum(PROP_DELAY_RANGE_35) as PD35," +
                "sum(PROP_DELAY_RANGE_36) as PD36," +
                "sum(PROP_DELAY_RANGE_37) as PD37," +
                "sum(PROP_DELAY_RANGE_38) as PD38," +
                "sum(PROP_DELAY_RANGE_39) as PD39," +
                "sum(PROP_DELAY_RANGE_40) as PD40," +
                "sum(PROP_DELAY_RANGE_41) as PD41," +
                "sum(TOTAL_COUNT) as TOTAL_COUNT " +
                "FROM sonpm.UTRAN_PROP_DELAY_MEASURE_COUNTERS WHERE CI in (" + sb.toString() + ") " +
                "AND end_time >= EXTRACT(EPOCH FROM TIMESTAMP WITH TIME ZONE '" + startTime + "')::int " +
                "AND end_time < EXTRACT(EPOCH FROM TIMESTAMP WITH TIME ZONE '" + endTime + "')::int " +
                //add RNC list
                "GROUP by CI" + grouporderdate + " ORDER by CI" + grouporderdate + " desc Limit 50000";
        List<Pd> pdList = null;
        try (PreparedStatement preparedStatement = getDbConnection(false).prepareStatement(select);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet == null) {
                preparedStatement.close();
                dbConnection.close();
                return null;
            }
            if (resultSet != null) {
                pdList = new ArrayList<>();
                String[] PDarray = new String[42];
                while (resultSet.next()) {
                    for (int i = 0; i < PDarray.length; i++) PDarray[i] = resultSet.getString("com.pavelk.main.Pd" + i);
                    for (int i = 0; i < cells.size(); i++) {
                        if (cells.get(i).getCellId() == Integer.parseInt(resultSet.getString("CI")))
                            if (agregate) {
                                pdList.add(new Pd(cells.get(i), endTime.toString(), resultSet.getString("TOTAL_COUNT"),
                                        PDarray));
                            } else {
                                pdList.add(new Pd(cells.get(i), resultSet.getString("date"), resultSet.getString("TOTAL_COUNT"),
                                        PDarray));
                            }
                    }
                }
            }
            dbConnection.close();
        } catch (SQLException e) {
            ControllerPDCellsToKML.logInfo.setLogData("SQL request error from UTRAN_PROP_DELAY_MEASURE_COUNTERS" + e + '\n');
            e.printStackTrace();
        } finally {
            if (dbConnection != null) dbConnection.close();
        }
        ControllerPDCellsToKML.logInfo.setLogData("SQL request from UTRAN_PROP_DELAY_MEASURE_COUNTERS is ok" + '\n');
        return pdList;
    }

}
