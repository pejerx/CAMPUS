import { ChangeEvent, FormEvent } from "react";
import { IconUpload } from "@tabler/icons-react";

type Props = {
  firstName: string;
  lastName: string;
  email: string;
  contactNumber: string;

  itemDescription: string;
  additionalInformation: string;

  previewImage: string;
  loading: boolean;

  onDescriptionChange: (value: string) => void;
  onAdditionalInformationChange: (value: string) => void;

  onImageChange: (
    event: ChangeEvent<HTMLInputElement>
  ) => void;

  onCancel: () => void;

  onSubmit: (
    event: FormEvent<HTMLFormElement>
  ) => void;
};

function ClaimForm({
  firstName,
  lastName,
  email,
  contactNumber,

  itemDescription,
  additionalInformation,

  previewImage,
  loading,

  onDescriptionChange,
  onAdditionalInformationChange,

  onImageChange,

  onCancel,

  onSubmit,
}: Props) {
  return (
    <form
      className="claim-form"
      onSubmit={onSubmit}
    >
      <label>
        Full Name

        <input
          type="text"
          value={`${firstName} ${lastName}`}
          readOnly
        />
      </label>

      <label>
        Email Address

        <input
          type="email"
          value={email}
          readOnly
        />
      </label>

      <label>
        Contact Number

        <input
          type="text"
          value={contactNumber}
          readOnly
        />
      </label>

      <label>
        Describe the Item
        <span className="required"> *</span>

        <textarea
          rows={4}
          placeholder="Describe identifying features that prove this item belongs to you."
          value={itemDescription}
          onChange={(e) =>
            onDescriptionChange(e.target.value)
          }
          required
        />
      </label>

      <label>
        Additional Information

        <textarea
          rows={3}
          placeholder="Any additional information that may help verify ownership."
          value={additionalInformation}
          onChange={(e) =>
            onAdditionalInformationChange(
              e.target.value
            )
          }
        />
      </label>

      <label>
        Upload Proof of Ownership

        <div className="claim-upload">

          <label
            htmlFor="proofImage"
            className="claim-upload-btn"
          >
            <IconUpload size={18} />
            Choose Image
          </label>

          <input
            id="proofImage"
            type="file"
            accept="image/*"
            hidden
            onChange={onImageChange}
          />

          {previewImage && (
            <img
              src={previewImage}
              alt="Proof Preview"
              className="claim-proof-preview"
            />
          )}

        </div>
      </label>

      <div className="claim-actions">

        <button
          type="button"
          className="explore-btn outline"
          onClick={onCancel}
        >
          Cancel
        </button>

        <button
          type="submit"
          className="explore-btn"
          disabled={loading}
        >
          {loading
            ? "Submitting..."
            : "Submit Claim Request"}
        </button>

      </div>
    </form>
  );
}

export default ClaimForm;