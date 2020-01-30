import { Moment } from 'moment';
import { IAgentDeclarant } from 'app/shared/model/agent-declarant.model';
import { IVehicule } from 'app/shared/model/vehicule.model';

export interface IFicheIncident {
  id?: number;
  dateIncident?: Moment;
  numeroFiche?: string;
  incidentCritique?: boolean;
  lieuIncident?: string;
  nombreVoyageur?: number;
  descriptionIncident?: string;
  agentDeclarant?: IAgentDeclarant;
  vehicule?: IVehicule;
}

export const defaultValue: Readonly<IFicheIncident> = {
  incidentCritique: false
};
