import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IAgentDeclarant, defaultValue } from 'app/shared/model/agent-declarant.model';

export const ACTION_TYPES = {
  FETCH_AGENTDECLARANT_LIST: 'agentDeclarant/FETCH_AGENTDECLARANT_LIST',
  FETCH_AGENTDECLARANT: 'agentDeclarant/FETCH_AGENTDECLARANT',
  CREATE_AGENTDECLARANT: 'agentDeclarant/CREATE_AGENTDECLARANT',
  UPDATE_AGENTDECLARANT: 'agentDeclarant/UPDATE_AGENTDECLARANT',
  DELETE_AGENTDECLARANT: 'agentDeclarant/DELETE_AGENTDECLARANT',
  RESET: 'agentDeclarant/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IAgentDeclarant>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type AgentDeclarantState = Readonly<typeof initialState>;

// Reducer

export default (state: AgentDeclarantState = initialState, action): AgentDeclarantState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_AGENTDECLARANT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_AGENTDECLARANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_AGENTDECLARANT):
    case REQUEST(ACTION_TYPES.UPDATE_AGENTDECLARANT):
    case REQUEST(ACTION_TYPES.DELETE_AGENTDECLARANT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_AGENTDECLARANT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_AGENTDECLARANT):
    case FAILURE(ACTION_TYPES.CREATE_AGENTDECLARANT):
    case FAILURE(ACTION_TYPES.UPDATE_AGENTDECLARANT):
    case FAILURE(ACTION_TYPES.DELETE_AGENTDECLARANT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_AGENTDECLARANT_LIST):
      return {
        ...state,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_AGENTDECLARANT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_AGENTDECLARANT):
    case SUCCESS(ACTION_TYPES.UPDATE_AGENTDECLARANT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_AGENTDECLARANT):
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

const apiUrl = 'api/agent-declarants';

// Actions

export const getEntities: ICrudGetAllAction<IAgentDeclarant> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_AGENTDECLARANT_LIST,
    payload: axios.get<IAgentDeclarant>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IAgentDeclarant> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_AGENTDECLARANT,
    payload: axios.get<IAgentDeclarant>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IAgentDeclarant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_AGENTDECLARANT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IAgentDeclarant> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_AGENTDECLARANT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IAgentDeclarant> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_AGENTDECLARANT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
