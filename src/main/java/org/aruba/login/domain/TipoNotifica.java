package org.aruba.login.domain;

public enum TipoNotifica
{
    SMS("Sms"),
    EMAIL("Email"),
    WHATSAPP("Whatsapp"),
    SLACK("Slack");

    private final String tipo;

    private TipoNotifica(final String tipo)
    {
        this.tipo = tipo;
    }

    @Override
    public String toString()
    {
        return tipo;
    }
}
