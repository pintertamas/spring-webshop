#include "backend.h"
#include <QQmlContext>
#include <QGuiApplication>
#include <QQmlApplicationEngine>

int main(int argc, char *argv[]) {
    QGuiApplication app(argc, argv);

    qmlRegisterType<Backend>("D:/bme/alkfejl/alf2022t-qts", 1, 0, "Backend");

    auto engine = QQmlApplicationEngine();
        const QUrl url(QStringLiteral("qrc:/main.qml"));
        QObject::connect(
            &engine,
            &QQmlApplicationEngine::objectCreated,
            &app,
            [url](QObject *obj, const QUrl &objUrl) {
                if (!obj && url == objUrl)
                    QCoreApplication::exit(-1);
            },
            Qt::QueuedConnection);
        engine.load(url);

    return app.exec();
}
