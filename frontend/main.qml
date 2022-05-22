import QtQuick
import QtQuick 2.15
import QtQuick.Controls 2.15
import QtQuick.Controls.Material 2.12
import "D:/bme/alkfejl/alf2022t-qts"

ApplicationWindow {
    id: mainPage
    visibility: "Maximized"
    visible: true

    Backend {
           id: backend
       }

    StackView {
           id: stack
           initialItem: mainView
           anchors.fill: parent

        }

    header: ToolBar {
        Row {
            spacing: 10
            anchors.fill: parent
            Button {
                id: homeButton
                text: "HOME"
                onClicked: {
                    stack.pop()
                    stack.push(mainView)
                }
            }

            Button {
                id: manButton
                text: "SHOP"
                onClicked : {
                    stack.pop()
                    stack.push(Qt.resolvedUrl("/Shop.qml"))
                }
            }


            Button {
                id: fileButton
                text: "USER"
                anchors.right: parent.TopRight;
                onClicked: user_menu.open()
                Menu {
                    id: user_menu
                    title: qsTr("&USER")
                    MenuItem {
                        text: qsTr("&CART")
                        onClicked : {
                            stack.pop()
                            stack.push(Qt.resolvedUrl("/Cart.qml"))
                        }
                    }
                    MenuItem {
                        text: qsTr("&CREAT ITEM")
                        onClicked : {
                            stack.pop()
                            stack.push(Qt.resolvedUrl("/CreateItem.qml"))
                        }
                    }
                    MenuItem {
                        text: qsTr("&LOGIN")
                        onClicked : {
                            stack.pop()
                            stack.push(Qt.resolvedUrl("/Login.qml"))
                        }
                    }
                    MenuItem {
                        text: qsTr("&REGISTRATION")
                        onClicked : {
                            stack.pop()
                            stack.push(Qt.resolvedUrl("/Register.qml"))
                        }
                    }
                    MenuSeparator { }
                    MenuItem {
                        text: qsTr("&QUIT")
                        onClicked : {

                            stack.pop()
                            stack.push(mainView)
                        }
                    }
                 }
            }
        }
    }
    Component {
        id: mainView

            Text{
                anchors.horizontalCenter: mainPage.horizontalCenter
                anchors.verticalCenter: mainPage.verticalCenter
                text: "WELCOME"
            }

    }

    footer: ToolBar {
        Row{
            anchors.horizontalCenter: parent.horizontalCenter
            Text {
                id: nam
                text: qsTr("QTS")
            }
        }
    }
}
