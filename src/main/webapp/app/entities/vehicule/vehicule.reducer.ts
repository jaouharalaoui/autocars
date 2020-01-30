import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IVehicule, defaultValue } from 'app/shared/model/vehicule.model';

export const ACTION_TYPES = {
  FETCH_VEHICULE_LIST: 'vehicule/FETCH_VEHICULE_LIST',
  FETCH_VEHICULE: 'vehicule/FETCH_VEHICULE',
  CREATE_VEHICULE: 'vehicule/CREATE_VEHICULE',
  UPDATE_VEHICULE: 'vehicule/UPDATE_VEHICULE',
  DELETE_VEHICULE: 'vehicule/DELETE_VEHICULE',
  RESET: 'vehicule/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IVehicule>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type VehiculeState = Readonly<typeof initialState>;

// Reducer

export default (state: VehiculeState = initialState, action): VehiculeState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_VEHICULE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_VEHICULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_VEHICULE):
    case REQUEST(ACTION_TYPES.UPDATE_VEHICULE):
    case REQUEST(ACTION_TYPES.DELETE_VEHICULE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_VEHICULE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_VEHICULE):
    case FAILURE(ACTION_TYPES.CREATE_VEHICULE):
    case FAILURE(ACTION_TYPES.UPDATE_VEHICULE):
    case FAILURE(ACTION_TYPES.DELETE_VEHICULE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_VEHICULE_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_VEHICULE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_VEHICULE):
    case SUCCESS(ACTION_TYPES.UPDATE_VEHICULE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_VEHICULE):
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

const apiUrl = 'api/vehicules';

// Actions

export const getEntities: ICrudGetAllAction<IVehicule> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_VEHICULE_LIST,
    payload: axios.get<IVehicule>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IVehicule> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_VEHICULE,
    payload: axios.get<IVehicule>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IVehicule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_VEHICULE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IVehicule> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_VEHICULE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IVehicule> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_VEHICULE,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
