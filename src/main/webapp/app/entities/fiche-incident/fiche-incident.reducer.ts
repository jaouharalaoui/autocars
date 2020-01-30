import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IFicheIncident, defaultValue } from 'app/shared/model/fiche-incident.model';

export const ACTION_TYPES = {
  FETCH_FICHEINCIDENT_LIST: 'ficheIncident/FETCH_FICHEINCIDENT_LIST',
  FETCH_FICHEINCIDENT: 'ficheIncident/FETCH_FICHEINCIDENT',
  CREATE_FICHEINCIDENT: 'ficheIncident/CREATE_FICHEINCIDENT',
  UPDATE_FICHEINCIDENT: 'ficheIncident/UPDATE_FICHEINCIDENT',
  DELETE_FICHEINCIDENT: 'ficheIncident/DELETE_FICHEINCIDENT',
  RESET: 'ficheIncident/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IFicheIncident>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type FicheIncidentState = Readonly<typeof initialState>;

// Reducer

export default (state: FicheIncidentState = initialState, action): FicheIncidentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_FICHEINCIDENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_FICHEINCIDENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_FICHEINCIDENT):
    case REQUEST(ACTION_TYPES.UPDATE_FICHEINCIDENT):
    case REQUEST(ACTION_TYPES.DELETE_FICHEINCIDENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_FICHEINCIDENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_FICHEINCIDENT):
    case FAILURE(ACTION_TYPES.CREATE_FICHEINCIDENT):
    case FAILURE(ACTION_TYPES.UPDATE_FICHEINCIDENT):
    case FAILURE(ACTION_TYPES.DELETE_FICHEINCIDENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_FICHEINCIDENT_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_FICHEINCIDENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_FICHEINCIDENT):
    case SUCCESS(ACTION_TYPES.UPDATE_FICHEINCIDENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_FICHEINCIDENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/fiche-incidents';

// Actions

export const getEntities: ICrudGetAllAction<IFicheIncident> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_FICHEINCIDENT_LIST,
    payload: axios.get<IFicheIncident>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IFicheIncident> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_FICHEINCIDENT,
    payload: axios.get<IFicheIncident>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IFicheIncident> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_FICHEINCIDENT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IFicheIncident> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_FICHEINCIDENT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IFicheIncident> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_FICHEINCIDENT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
