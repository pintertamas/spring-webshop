import QtQuick 2.0
import QtQuick.Controls 2.15
import QtQuick.Layouts 1.0

Item {
    ColumnLayout {
      Layout.fillWidth: true
      Layout.fillHeight: true

      GridLayout {
        id: shopView
        columns: 7
        rows: 4

        Layout.fillWidth: true
        width: 1000
        Repeater {
          id: shopItems
          model: backend.items

          Cloth {}
        }
      }
    }
}
