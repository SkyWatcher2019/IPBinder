package ru.skywatcher_2019.IPBinder;

public class Messages {
    public static String kick(String ip) {
        return "§eПодключение данного аккаунта доступно только с разрешенных IP. \n§eВаш IP - §b%ip%§e, добавьте его в исключения.".replace("%ip%", ip);
    }

    public static String bindedIP(String nickname, String ip) {
        return "§eАккаунт §b%nickname% §eпривязан к IP §b%ip%".replace("%nickname%", nickname).replace("%ip%", ip);
    }

    public static String unbindedIP(String nickname, String ip) {
        return "§eАккаунт §b%nickname% §eотвязан от IP §b%ip%".replace("%nickname%", nickname).replace("%ip%", ip);
    }

    public static String alreadyBindedIP(String nickname, String ip) {
        return "§eАккаунт §b%nickname% §eуже привязан к IP §b%ip%".replace("%nickname%", nickname).replace("%ip%", ip);
    }

    public static String noBindedIP(String nickname) {
        return "§eАккаунт §b%nickname% §eне привязан к данному IP".replace("%nickname%", nickname);
    }

    public static String noPermission = "§cНедостаточно прав для выполнения данной команды";

    public static String emptyBindedIPs(String nickname) {
        return "§eАккаунт §b%nickname% §eне имеет привязанных IP".replace("%nickname%", nickname);
    }
}
