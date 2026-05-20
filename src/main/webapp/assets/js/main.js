if (!window.quickFixUiReady) {
  window.quickFixUiReady = true;

  function syncSelectedProvider(select) {
    const provider = select?.selectedOptions[0]?.dataset.provider;
    const input = document.querySelector('input[name="providerId"]');
    if (provider && input) input.value = provider;
  }

  function setupRevealMotion() {
    if (window.matchMedia("(prefers-reduced-motion: reduce)").matches) return;

    const selectors = [
      ".hero__label",
      ".hero__title",
      ".hero__copy",
      ".search-row",
      ".hero__actions",
      ".hero-panel",
      ".section__header",
      ".market-hero",
      ".market-section__header",
      ".page__header",
      ".card",
      ".booking-card",
      ".request-card",
      ".empty-state"
    ];

    const targets = Array.from(document.querySelectorAll(selectors.join(",")));
    targets.forEach((element, index) => {
      element.classList.add("reveal-target");
      element.style.setProperty("--reveal-delay", `${Math.min(index % 8, 7) * 45}ms`);
    });

    if (!("IntersectionObserver" in window)) {
      targets.forEach((element) => element.classList.add("is-visible"));
      return;
    }

    const observer = new IntersectionObserver((entries) => {
      entries.forEach((entry) => {
        if (!entry.isIntersecting) return;
        entry.target.classList.add("is-visible");
        observer.unobserve(entry.target);
      });
    }, { threshold: 0.12, rootMargin: "0px 0px -40px 0px" });

    targets.forEach((element) => observer.observe(element));
  }

  function setupDigitOnlyFields() {
    document.querySelectorAll("[data-digits-only]").forEach((input) => {
      input.addEventListener("input", function () {
        const cleaned = input.value.replace(/[^\d+]/g, "").replace(/(?!^)\+/g, "");
        if (input.value !== cleaned) input.value = cleaned;
      });
    });
  }

  document.addEventListener("change", function (event) {
    if (event.target.name === "bookingId") syncSelectedProvider(event.target);
  });

  document.addEventListener("click", function (event) {
    const isTrayClick = event.target.closest(".notification-tray");
    if (!isTrayClick) {
      document.querySelectorAll(".notification-tray__list.is-open").forEach(list => {
        list.classList.remove("is-open");
      });
    }
  });

  document.addEventListener("DOMContentLoaded", function () {
    document.body.classList.add("motion-ready");
    syncSelectedProvider(document.querySelector('select[name="bookingId"]'));
    setupDigitOnlyFields();
    setupRevealMotion();
  });
}
