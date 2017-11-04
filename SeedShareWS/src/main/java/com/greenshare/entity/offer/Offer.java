package com.greenshare.entity.offer;

import static javax.persistence.GenerationType.SEQUENCE;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.greenshare.entity.FlowerShop;
import com.greenshare.entity.abstracts.AbstractPhotogenicEntity;
import com.greenshare.entity.address.Address;
import com.greenshare.entity.user.User;
import com.greenshare.entity.vegetable.Species;
import com.greenshare.enumeration.OfferStatus;
import com.greenshare.enumeration.OfferType;
import com.greenshare.enumeration.PhotoType;

import java.util.ArrayList;
import java.util.List;

/**
 * Persistence class for the table offer
 * 
 * @author joao.silva
 */
@Entity
@Table(name = "offer")
public class Offer extends AbstractPhotogenicEntity<Offer> implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String SEQUENCE_NAME = "offer_seq";

	private static final PhotoType PHOTO_TYPE = PhotoType.OFFER;

	@Id
	@GeneratedValue(strategy = SEQUENCE, generator = SEQUENCE_NAME)
	@SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME)
	@Basic(optional = false)
	@Column(name = "offer_id")
	private Long id;

	@Basic(optional = false)
	@NotNull(message = "O preço unitário não pode ser nulo.")
	@Min(0)
	@Column(name = "unit_price")
	private Float unitPrice;

	@Basic(optional = false)
	@NotNull(message = "Quantidade restante não pode ser nula")
	@Min(0)
	@Max(9999)
	@Column(name = "remaining_amount")
	private Integer remainingAmount;

	@Basic(optional = false)
	@Column(name = "initial_amount")
	@Min(1)
	@Max(9999)
	private Integer initialAmount;

	@Basic(optional = false)
	@Column(name = "offer_status")
	private Integer offerStatus;

	@Basic(optional = false)
	@Column(name = "type")
	private Integer type;
	
	@Basic(optional = false)
	@NotNull(message = "Idade da muda não pode ser nula.")
	@Column(name = "product_age")
	private Integer productAge;

	@ManyToOne
	@Basic(optional = false)
	@NotNull(message = "O usuário não pode ser nulo.")
	@Valid
	@JoinColumn(name = "user_id")
	private User user;

	@Valid
	@OneToMany(mappedBy = "offer")
	private List<OfferComment> offerComments;

	@ManyToOne
	@Basic(optional = true)
	@Valid
	@JoinColumn(name = "flower_shop_id")
	private FlowerShop flowerShop;

	@ManyToOne
	@Basic(optional = false)
	@NotNull(message = "A espécie não pode ser nula.")
	@Valid
	@JoinColumn(name = "species_id")
	private Species species;

	@Basic(optional = false)
	@NotNull(message = "O endereço não pode ser nulo.")
	@Valid
	@OneToOne
	@JoinColumn(name = "address_id")
	private Address address;

	@JsonIgnore
	@Valid
	@OneToMany(mappedBy = "offer")
	private List<Request> requests;

	@Basic(optional = false)
	@NotNull(message = "A descrição não pode ser nula.")
	@Size(min = 1, max = 2500, message = "A descrição deve conter de 1 a 2500 caracteres.")
	@Column(name = "description", columnDefinition = "TEXT", length = 2500)
	private String description;

	protected Offer() {
		super(PHOTO_TYPE, false);
		this.validationErrors = new ArrayList<String>();
	}

	public Offer(Float unitPrice, Integer remainingAmount, User user, Species species, String description,
			FlowerShop flowerShop, Address address, Integer productAge) {
		super(PHOTO_TYPE, true);
		if (this.unitPrice == null || this.unitPrice == (float) 0) {
			this.type = OfferType.Donation.getOfferType();
			this.unitPrice = (float) 0;
		} else {
			this.type = OfferType.Sale.getOfferType();
			this.unitPrice = unitPrice;
		}
		if (user.getIsLegalPerson()) {
			this.flowerShop = flowerShop;
		}
		this.productAge = productAge;
		this.address = address;
		this.remainingAmount = remainingAmount;
		this.initialAmount = remainingAmount;
		this.species = species;
		this.offerStatus = OfferStatus.Active.getValue();
		this.description = description;
	}

	@JsonIgnore
	public boolean isValid() {
		this.validationErrors.clear();

		if (isNullOrEmpty(this.description) || is(this.description).orSmallerThan(1).orBiggerThan(2500)) {
			this.validationErrors.add("Descrição inválida.");
		}
		if(isNull(this.productAge) || is(this.productAge).smallerThan(0)) {
			this.validationErrors.add("Idade da muda inválida.");
		}
		if (isNotNull(this.type) && isNotNull(this.unitPrice)) {
			if (this.type == OfferType.Sale.getOfferType() && is(this.unitPrice).smallerOrEqual(0)) {
				this.validationErrors.add("Preço unitário inválido para uma venda.");
			}
			if (this.type == OfferType.Donation.getOfferType() && is(this.unitPrice).notEqual(0)) {
				this.validationErrors.add("Preço unitário inválido para uma doação.");
			}
		} else {
			if (isNull(this.type)) {
				this.validationErrors.add("Tipo de oferta inválida.");
			}
			if (isNull(this.unitPrice)) {
				this.validationErrors.add("Preço unitário inválido.");
			}
		}
		if (isNull(this.remainingAmount) || is(this.remainingAmount).orSmallerThan(0).orBiggerThan(9999)) {
			this.validationErrors.add("Quantidade restante inválida.");
		}
		if (isNull(this.initialAmount) || is(this.initialAmount).orSmallerThan(1).orBiggerThan(9999)) {
			this.validationErrors.add("Quantidade inicial inválida.");
		}
		if (isNull(this.user)) {
			this.validationErrors.add("O usuário não pode ser nulo.");
		} else if (this.user.isNotValid()) {
			this.validationErrors.addAll(this.user.getValidationErrors());
		}
		if (isNull(this.species)) {
			this.validationErrors.add("A espécie não pode ser nula.");
		} else if (this.species.isNotValid()) {
			this.validationErrors.addAll(this.species.getValidationErrors());
		}
		if (isNotNull(this.flowerShop) && this.flowerShop.isNotValid()) {
			this.validationErrors.addAll(this.species.getValidationErrors());
		}
		if (isNull(this.address)) {
			this.validationErrors.add("O endereço não pode ser nulo.");
		} else if (this.address.isNotValid()) {
			this.validationErrors.addAll(this.address.getValidationErrors());
		}
		if(isNull(this.offerStatus)) {
			this.validationErrors.add("Status da oferta não pode ser nulo.");
		}else if(OfferStatus.exists(this.offerStatus)){
			this.validationErrors.add("Status de oferta inexistente.");
		}
		return this.validationErrors.isEmpty();
	}

	public Long getId() {
		return this.id;
	}

	public Float getUnitPrice() {
		return this.unitPrice;
	}

	public Integer getRemainingAmount() {
		return this.remainingAmount;
	}

	public void setRemainingAmount(Integer remainingAmount) {
		this.remainingAmount = remainingAmount;
	}

	public Integer getInitialAmount() {
		return this.initialAmount;
	}

	public Integer getOfferStatus() {
		return this.offerStatus;
	}

	public void setOfferStatus(OfferStatus offerStatus) {
		this.offerStatus = offerStatus.getValue();
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(OfferType type) {
		this.type = type.getOfferType();
	}

	public User getUser() {
		return this.user;
	}

	public Species getSpecies() {
		return this.species;
	}

	public List<Request> getRequests() {
		return this.requests;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<OfferComment> getOfferComments() {
		return this.offerComments;
	}

	public FlowerShop getFlowerShop() {
		return this.flowerShop;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Integer getProductAge() {
		return this.productAge;
	}

	@Override
	public void update(Offer e) {
		this.description = e.getDescription();
	}
}