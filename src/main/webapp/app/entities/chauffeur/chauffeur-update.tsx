import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IVehicule } from 'app/shared/model/vehicule.model';
import { getEntities as getVehicules } from 'app/entities/vehicule/vehicule.reducer';
import { getEntity, updateEntity, createEntity, reset } from './chauffeur.reducer';
import { IChauffeur } from 'app/shared/model/chauffeur.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IChauffeurUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IChauffeurUpdateState {
  isNew: boolean;
  idsvehicule: any[];
}

export class ChauffeurUpdate extends React.Component<IChauffeurUpdateProps, IChauffeurUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsvehicule: [],
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

    this.props.getVehicules();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { chauffeurEntity } = this.props;
      const entity = {
        ...chauffeurEntity,
        ...values,
        vehicules: mapIdList(values.vehicules)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/chauffeur');
  };

  render() {
    const { chauffeurEntity, vehicules, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="autocarsApp.chauffeur.home.createOrEditLabel">
              <Translate contentKey="autocarsApp.chauffeur.home.createOrEditLabel">Create or edit a Chauffeur</Translate>
            </h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : chauffeurEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">
                      <Translate contentKey="global.field.id">ID</Translate>
                    </Label>
                    <AvInput id="chauffeur-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nomLabel" for="nom">
                    <Translate contentKey="autocarsApp.chauffeur.nom">Nom</Translate>
                  </Label>
                  <AvField
                    id="chauffeur-nom"
                    type="text"
                    name="nom"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="prenomLabel" for="prenom">
                    <Translate contentKey="autocarsApp.chauffeur.prenom">Prenom</Translate>
                  </Label>
                  <AvField
                    id="chauffeur-prenom"
                    type="text"
                    name="prenom"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="telephoneLabel" for="telephone">
                    <Translate contentKey="autocarsApp.chauffeur.telephone">Telephone</Translate>
                  </Label>
                  <AvField
                    id="chauffeur-telephone"
                    type="text"
                    name="telephone"
                    validate={{
                      required: { value: true, errorMessage: translate('entity.validation.required') }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="vehicules">
                    <Translate contentKey="autocarsApp.chauffeur.vehicule">Vehicule</Translate>
                  </Label>
                  <AvInput
                    id="chauffeur-vehicule"
                    type="select"
                    multiple
                    className="form-control"
                    name="vehicules"
                    value={chauffeurEntity.vehicules && chauffeurEntity.vehicules.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {vehicules
                      ? vehicules.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/chauffeur" replace color="info">
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
  vehicules: storeState.vehicule.entities,
  chauffeurEntity: storeState.chauffeur.entity,
  loading: storeState.chauffeur.loading,
  updating: storeState.chauffeur.updating,
  updateSuccess: storeState.chauffeur.updateSuccess
});

const mapDispatchToProps = {
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
)(ChauffeurUpdate);
