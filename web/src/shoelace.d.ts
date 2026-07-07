import type { DetailedHTMLProps, HTMLAttributes } from "react";

declare module "react" {
  namespace JSX {
    interface IntrinsicElements {
      "sl-button": DetailedHTMLProps<
        HTMLAttributes<HTMLElement>,
        HTMLElement
      > & {
        size?: "small" | "medium" | "large";
        pill?: boolean;
        outline?: boolean;
        variant?: string;
      };
    }
  }
}