import { IVehicule } from 'app/shared/model/vehicule.model';

export interface IChauffeur {
  id?: number;
  nom?: string;
  prenom?: string;
  telephone?: string;
  vehicules?: IVehicule[];
}

export const defaultValue: Readonly<IChauffeur> = {};
