#ifndef BACKEND_H
#define BACKEND_H

#include <QObject>
#include <QNetworkAccessManager>
#include <QVariant>
#include <QNetworkRequest>
#include <QNetworkReply>
#include <QDebug>
#include <QJsonDocument>
#include <QJsonObject>
#include <QJsonArray>
#include <QEventLoop>
#include <QUrlQuery>

struct Item {
    Q_GADGET
public:
    int id ;
    QString name;
    QString description;
    double price = 0.0;
    QString color;
    QString gender;
    QString size;
    int sku;
    QString category;
    QString discount;
    QString carts;
    Q_PROPERTY(int id MEMBER id)
    Q_PROPERTY(QString name MEMBER name)
    Q_PROPERTY(QString description MEMBER description)
    Q_PROPERTY(double price MEMBER price)
    Q_PROPERTY(QString color MEMBER color)
    Q_PROPERTY(QString gender MEMBER gender)
    Q_PROPERTY(QString size MEMBER size)
    Q_PROPERTY(int sku MEMBER sku)
    Q_PROPERTY(QString category MEMBER category)
    Q_PROPERTY(QString discount MEMBER discount)
    Q_PROPERTY(QString carts MEMBER carts)
};

class Backend : public QObject {
    Q_OBJECT
    Q_PROPERTY(QVector<Item> items READ items NOTIFY loadItem)
    Q_PROPERTY(QVector<cartItem> cartItems READ cartItems NOTIFY loadCart)

    QNetworkAccessManager *manager;

public:
    explicit Backend(QObject *parent = nullptr);

    QVector<Item> cartItems();
    QVector<Item> items();

public slots:

    void registration(QString username, QString password, QString email, QString telephone);
    void login(QString username, QString password);
    void editUser(QString username, QString email, QString telephone);
    void logout();


    void addtoCart(Item clothItem);
    void removefromCart(Item clothItem);

    void newItem(QString name,QString description, QString price, QString color, QString gender, QString size, QString sku, QString category);
    void editItem(Item editItem);
    void deleteItem(Item clothItem);

    void createOrder();

signals:
     void loadItem();
     void loadCart();

private :

    QVector<Item> shop_Items;
    QVector<Item> cart_Items;
    QString token;

    void LoginResponse(QNetworkReply *res);
    void ItemLoader(QNetworkReply *res);
    void ItemLoaderCart(QNetworkReply *res);
};

#endif // BACKEND_H
