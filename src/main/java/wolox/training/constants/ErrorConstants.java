package wolox.training.constants;

import com.google.common.base.Preconditions;

public final class ErrorConstants {


    public static final String NOT_EXIST_TITTLE ="El libro con el TITULO ingresado no existe";
    public static final String NOT_MATCH_UPDATE =" EL valor a actualizar no correspnde a un valor existente";
    public static final String NOT_EXIST_ID ="El libro con el id ingresado no existe";
    public static final String EXIST_BOOK ="El libro que pretende ingresar ya existe";
    public static final String NOT_EXIST_BOOK_DELETE ="El libro mencionado no se encuentra en el registro";
    public static final String NOT_EXIST_USER_ID ="El Usuario con el id ingresado no existe";

    /**
     * Null and empty fields
     */
    public static final String NULL_FIELD_GENRE ="The genre field cannot be null.";
    public static final String EMPTY_FIELD_GENRE ="The genre field cannot be empty.";
    public static final String NULL_FIELD_AUTHOR ="The author field cannot be null.";
    public static final String EMPTY_FIELD_AUTHOR ="The author field cannot be empty.";
    public static final String NULL_FIELD_TITLE ="The title field cannot be null.";
    public static final String EMPTY_FIELD_TITLE ="The title field cannot be empty.";
    public static final String NULL_FIELD_SUBTITLE ="The subtitle field cannot be null.";
    public static final String EMPTY_FIELD_SUBTITLE ="The subtitle field cannot be empty.";
    public static final String NULL_FIELD_PUBLISHER ="The publisher field cannot be null.";
    public static final String EMPTY_FIELD_PUBLISHER ="The publisher field cannot be empty.";
    public static final String NULL_FIELD_YEAR ="The year field cannot be null.";
    public static final String EMPTY_FIELD_YEAR ="The year field cannot be empty.";
    public static final String NULL_FIELD_PAGES ="The pages field cannot be null.";
    public static final String EMPTY_FIELD_PAGES ="The page field should be  greater than 0";
    public static final String NULL_FIELD_USERNAME ="The username field cannot be null.";
    public static final String EMPTY_FIELD_USERNAME ="The username field cannot be empty.";
    public static final String NULL_FIELD_BIRTHDAY ="The birthday field cannot be null.";
    public static final String NOW_FIELD_BIRTHDAY ="The birthday field cannot be greater than date";
    public static final String NULL_FIELD_NAME ="The name field cannot be null.";
    public static final String EMPTY_FIELD_NAME ="The name field cannot be empty.";
    public static final String NULL_FIELD_ISBN ="The isbn field cannot be null.";
    public static final String EMPTY_FIELD_ISBN ="The isbn field cannot be empty.";
    public static final String NUMERIC_FIELD_ISBN ="The isbn field should be a number.";
    public static final String NUMERIC_FIELD_YEAR ="The year field should be a number.";
    private ErrorConstants() {

    }
}
