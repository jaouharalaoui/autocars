import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAgentDeclarant } from 'app/shared/model/agent-declarant.model';
import { getEntities as getAgentDeclarants } from 'app/entities/agent-declarant/agent-declarant.reducer';
import { IVehicule } from 'app/shared/model/vehicule.model';
import { getEntities as getVehicules } from 'app/entities/vehicule/vehicule.reducer';
import { getEntity, updateEntity, createEntity, reset } from './fiche-incident.reducer';
import { IFicheIncident } from 'app/shared/model/fiche-incident.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IFicheIncidentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IFicheIncidentUpdateState {
  isNew: boolean;
  agentDeclarantId: string;
  vehiculeId: string;
}

export class FicheIncidentUpdate extends React.Component<IFicheIncidentUpdateProps, IFicheIncidentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      agentDeclarantId: '0',
      vehiculeId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getAgentDeclarants();
    this.props.getVehicules();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { ficheIncidentEntity } = this.props;
      const entity = {
        ...ficheIncidentEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/fiche-incident');
  };

  render() {
    const { ficheIncidentEntity, agentDeclarants, vehicules, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="autocarsApp.ficheIncident.home.createOrEditLabel">
              <Translate contentKey="autocarsApp.ficheIncident.home.createOrEditLabel">Create or edit a FicheIncident</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : ficheIncidentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="fiche-incident-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="dateIncidentLabel" for="dateIncident">
                    <Translate contentKey="autocarsApp.ficheIncident.dateIncident">Date Incident</Translate>
                  </Label>
                  <AvField
                    id="fiche-incident-dateIncident"
                    type="date"
                    className="form-control"
                    name="dateIncident"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="numeroFicheLabel" for="numeroFiche">
                    <Translate contentKey="autocarsApp.ficheIncident.numeroFiche">Numero Fiche</Translate>
                  </Label>
                  <AvField
                    id="fiche-incident-numeroFiche"
                    type="text"
                    name="numeroFiche"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="incidentCritiqueLabel" check>
                    <AvInput id="fiche-incident-incidentCritique" type="checkbox" className="form-control" name="incidentCritique" />
                    <Translate contentKey="autocarsApp.ficheIncident.incidentCritique">Incident Critique</Translate>
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label id="lieuIncidentLabel" for="lieuIncident">
                    <Translate contentKey="autocarsApp.ficheIncident.lieuIncident">Lieu Incident</Translate>
                  </Label>
                  <AvField
                    id="fiche-incident-lieuIncident"
                    type="text"
                    name="lieuIncident"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="nombreVoyageurLabel" for="nombreVoyageur">
                    <Translate contentKey="autocarsApp.ficheIncident.nombreVoyageur">Nombre Voyageur</Translate>
                  </Label>
                  <AvField
                    id="fiche-incident-nombreVoyageur"
                    type="string"
                    className="form-control"
                    name="nombreVoyageur"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') },
                      number: { value: true, errorMessage: translate('entity.validation.number') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="descriptionIncidentLabel" for="descriptionIncident">
                    <Translate contentKey="autocarsApp.ficheIncident.descriptionIncident">Description Incident</Translate>
                  </Label>
                  <AvField id="fiche-incident-descriptionIncident" type="text" name="descriptionIncident" />
                </AvGroup>
                <AvGroup>
                  <Label for="agentDeclarant.nom">
                    <Translate contentKey="autocarsApp.ficheIncident.agentDeclarant">Agent Declarant</Translate>
                  </Label>
                  <AvInput id="fiche-incident-agentDeclarant" type="select" className="form-control" name="agentDeclarant.id">
                    <option value="" key="0" />
                    {agentDeclarants
                      ? agentDeclarants.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.nom}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="vehicule.immatriculation">
                    <Translate contentKey="autocarsApp.ficheIncident.vehicule">Vehicule</Translate>
                  </Label>
                  <AvInput id="fiche-incident-vehicule" type="select" className="form-control" name="vehicule.id">
                    <option value="" key="0" />
                    {vehicules
                      ? vehicules.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.immatriculation}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/fiche-incident" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">
                    <Translate contentKey="entity.action.back">Back</Translate>
                  </span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp;
                  <Translate contentKey="entity.action.save">Save</Translate>
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  agentDeclarants: storeState.agentDeclarant.entities,
  vehicules: storeState.vehicule.entities,
  ficheIncidentEntity: storeState.ficheIncident.entity,
  loading: storeState.ficheIncident.loading,
  updating: storeState.ficheIncident.updating,
  updateSuccess: storeState.ficheIncident.updateSuccess
});

const mapDispatchToProps = {
  getAgentDeclarants,
  getVehicules,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(FicheIncidentUpdate);
