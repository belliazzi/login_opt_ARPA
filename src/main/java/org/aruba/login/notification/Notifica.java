package org.aruba.login.notification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Notifica
{
    @JsonProperty("mittente")
    private String mittente;

    @JsonProperty("testo")
    private String testo;

    @JsonProperty("destinatario")
    public String destinatario;

    @JsonProperty("oggetto")
    public String oggetto;

    @JsonProperty("tipo")
    public String tipo;

    @JsonCreator
    public Notifica(final @JsonProperty("mittente") String mittente, final @JsonProperty("testo") String testo,
        final @JsonProperty("destinatario") String destinatario, final @JsonProperty("oggetto") String oggetto,
        final @JsonProperty("tipo") String tipo)
    {
        super();
        this.mittente = mittente;
        this.testo = testo;
        this.destinatario = destinatario;
        this.oggetto = oggetto;
        this.tipo = tipo;
    }

    @JsonCreator
    public Notifica()
    {
        super();

    }

    public String getTesto()
    {
        return testo;
    }

    public String getTipo()
    {
        return tipo;
    }

    public String getDestinatario()
    {
        return destinatario;
    }

    public void setDestinatario(final String destinatario)
    {
        this.destinatario = destinatario;
    }

    public String getOggetto()
    {
        return oggetto;
    }

    public void setOggetto(final String oggetto)
    {
        this.oggetto = oggetto;
    }

    public void setTesto(final String testo)
    {
        this.testo = testo;
    }

    public void setTipo(final String tipo)
    {
        this.tipo = tipo;
    }

    public String getMittente()
    {
        return mittente;
    }

    public void setMittente(final String mittente)
    {
        this.mittente = mittente;
    }

    // implementazione dell'interfaccia Serializable
    public static final long serialVersionUID = 1L;

}
