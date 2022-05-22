import QtQuick 2.0
import QtQuick.Controls 2.15
import QtQuick.Layouts 1.0


Item {
    id: manPage
    ColumnLayout {
        anchors.horizontalCenter: parent.horizontalCenter
        anchors.verticalCenter: parent.verticalCenter
        anchors.margins: 10
        spacing: 3

        TextField {
            id: loginName
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 10
            placeholderText: "Username"
        }

        TextField {
            id: loginPassword
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 10
            placeholderText: "Password"
            echoMode: TextInput.PasswordEchoOnEdit
        }
        Popup {
            id: popup
            height: 300
            width: 300
            anchors.centerIn: Overlay.overlay
            padding: 10

                contentItem: Text {
                    text: "LOGGED IN"
                }
       }

        Button {
            id: proccessButton
            Layout.preferredWidth: 200
            Layout.preferredHeight: 40
            font.pointSize: 15
            text: "LOGIN"
            enabled: loginName.text.length > 0 && loginPassword.text.length > 0
            onClicked: function() {
                backend.login(loginName.text,loginPassword.text);
                popup.open();

            }
        }
    }
}
