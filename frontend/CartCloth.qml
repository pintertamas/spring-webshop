import QtQuick 2.0
import QtQuick.Controls 2.15
import QtQuick.Layouts 1.15
import QtQuick.Controls.Material 2.12

Pane {
    Material.elevation: 6
    background: Rectangle {
        radius: 15
    }
    ColumnLayout {
        Layout.preferredHeight: 400
        Layout.preferredWidth: 400
        spacing: 2
        Text {
            text: modelData.name
        }
        Image {
            Layout.preferredHeight: 200
            Layout.preferredWidth: 200
            //source: ""
        }
        Text {
            text: modelData.gender
        }
        Text {
            text: modelData.description
        }
        Text {
            text: modelData.color
        }
        Text {
            text: modelData.size
        }
        Text {
            text: `${modelData.price} Ft`
        }
        Button {
            text: "REMOVE"
            onClicked: function() {
                backend.removefromCart(modelData);
            }
        }
    }
}
